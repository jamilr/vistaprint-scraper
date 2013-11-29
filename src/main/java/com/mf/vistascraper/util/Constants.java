package com.mf.vistascraper.util;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/19/13
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */

public class Constants {

    public static char[] ALPHABET = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    public static Map<String, String> SPECIAL_CHAR_MAP;

    static {
        SPECIAL_CHAR_MAP = new HashMap<String, String>();
        SPECIAL_CHAR_MAP.put("&#39;", "'");
        SPECIAL_CHAR_MAP.put("&#160;", "");
        SPECIAL_CHAR_MAP.put("&amp;", "&");
    }


    public static Set<String> SHEET_COLS_SET =
            new LinkedHashSet<String>(Arrays.asList(
                    new String[]{"Name", "Phone", "Web-site", "Email"}));

    public static String SHEET_NAME = "DATA";
    public static String PROPS_FILENAME = "app.properties";
    public static String PAGE_COUNT_TAG = "script:contains(BodyOnLoad)";
    public static String VISTAPRINT_URL_TEMPLATE = "http://directory.vistaprint.com/search.aspx?what=%s&where=%s";

    public static List<String> STATES;
    public static List<String> TOPICS;
    public static List<String> SECTIONS;
    public static List<String> BASETOPICS;

    public static final String FIELD_NAME = "text";

    static {

        STATES = new ArrayList<String>();
        TOPICS = new ArrayList<String>();
        SECTIONS = new ArrayList<String>(3);
        BASETOPICS = new ArrayList<String>();

        SECTIONS.add("Keywords:");
        SECTIONS.add("Categories:");
    }
}
