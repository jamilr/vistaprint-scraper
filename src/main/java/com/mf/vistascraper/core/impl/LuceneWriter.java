package com.mf.vistascraper.core.impl;

import com.mf.vistascraper.util.Constants;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/19/13
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */

public class LuceneWriter {

    private static Logger logger = Logger.getLogger(LuceneWriter.class);

    private Boolean isOpened;
    private String pathToIndex = "";
    private IndexWriter indexWriter = null;


    private LuceneWriter() {

    }

    public LuceneWriter(String pathToIndex) {
        this.pathToIndex = pathToIndex;
        this.isOpened = false;
    }

    public synchronized boolean openIndex(){

        if (indexWriter != null && isOpened)
            return isOpened;

        try {

            //Open the directory so lucene knows how to deal with it
            Directory dir = FSDirectory.open(new File(pathToIndex));

            //Chose the analyzer we are going to use to write documents to the index. We need to specify the version
            //of the Lucene index type we want to use
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);

            //Create an index writer configuration. Same thing here with the index version
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_45, analyzer);

            //we are always going to overwrite the index that is currently in the directory
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

            if (indexWriter == null && !isOpened) {
                indexWriter = new IndexWriter(dir, iwc);
                isOpened = true;
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return isOpened;
        }
        return isOpened;
    }

    public synchronized void addText(String text) {

        Document doc = new Document();
        doc.add(new TextField(Constants.FIELD_NAME, text, Field.Store.YES));
        try {

            indexWriter.addDocument(doc);
            logger.info("New Document has been added to Lucene indexer");
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void close(){

        logger.info("about to close the writer");

        try {

            indexWriter.commit();
            indexWriter.close();
            isOpened = false;
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public Boolean isOpened() {
        return isOpened;
    }
}
