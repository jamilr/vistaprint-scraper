package com.mf.vistascraper.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/19/13
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */

public class PropertiesUtil {

    private static Properties prop;

    static {
        prop = new Properties();
        loadProperties();
    }

    public static Properties getInstance() {
        return prop;
    }

    private static Properties loadProperties() {
        try {
            prop.load( new FileInputStream(Constants.PROPS_FILENAME));
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return prop;
    }


}
