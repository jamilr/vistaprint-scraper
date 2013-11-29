package com.mf.vistascraper.core.impl;

import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.mf.vistascraper.util.ByValueMapEntryComparator;
import com.mf.vistascraper.util.Constants;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/19/13
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class TermRankAnalyzer {

    private String indexerFilePath;
    private IndexReader indexReader;

    public TermRankAnalyzer() {

    }

    public void setIndexerFilePath(String indexerFilePath) {
        this.indexerFilePath = indexerFilePath;
    }

    public List<String> getTopRankedTerms(Integer k) {

        openIndexer();

        Terms terms = getTerms(Constants.FIELD_NAME);
        List<String> termKeywords = rankTerms(terms);

        if (!termKeywords.isEmpty() && termKeywords.size() > k)
            return termKeywords.subList(0, k);
        else
            return termKeywords;
    }

    private List<String> rankTerms(Terms terms) {

        List<String> termList = null;
        Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
        BytesRef byteRef;
        String term;
        int termFrq;
        try {

            TermsEnum iterator = terms.iterator(null);
            while((byteRef = iterator.next()) != null) {
                term = byteRef.utf8ToString();
                termFrq = iterator.docFreq();
                frequencyMap.put(term, termFrq);
            }

            frequencyMap = sortTermMap(frequencyMap);
            termList = new ArrayList<String>(frequencyMap.keySet());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return termList;
    }

    private Map<String, Integer> sortTermMap(Map<String, Integer> frequencyMap) {

        List<Map.Entry<String, Integer>> entries =
                new ArrayList<Map.Entry<String, Integer>>(frequencyMap.entrySet());

        Collections.sort(entries, new ByValueMapEntryComparator());
        frequencyMap.clear();
        frequencyMap = new LinkedHashMap<String, Integer>();

        for (Map.Entry<String, Integer> entry: entries)
            frequencyMap.put(entry.getKey(), entry.getValue());

        return frequencyMap;
    }

    private void openIndexer() {

        File path = new File(indexerFilePath);
        try {
            Directory index = FSDirectory.open(path);
            indexReader = DirectoryReader.open(index);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Terms getTerms(String fieldName) {

        Terms terms = null;
        try {

            Fields fields = MultiFields.getFields(indexReader);
            terms = fields.terms(fieldName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return terms;
    }

}
