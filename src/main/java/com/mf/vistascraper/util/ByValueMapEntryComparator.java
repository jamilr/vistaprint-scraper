package com.mf.vistascraper.util;

import java.util.Comparator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/19/13
 * Time: 7:54 PM
 * To change this template use File | Settings | File Templates.
 */

public class ByValueMapEntryComparator implements Comparator<Map.Entry<String, Integer>> {

    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        if (o1.getValue() == null && o2.getValue() == null) return 0;
        if (o1.getValue() == null) return -1; //Nulls last
        return - o1.getValue().compareTo(o2.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
