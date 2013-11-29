package com.mf.vistascraper;

import com.mf.vistascraper.model.BusinessEntity;
import com.mf.vistascraper.spreadsheet.DataRegistry;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/29/13
 * Time: 7:40 AM
 * To change this template use File | Settings | File Templates.
 */

public class DataRegistryTest {

    private static DataRegistry dataRegistry;

    @Before
    public void setUpClass() {
        dataRegistry = new DataRegistry();
    }

    @Test
    public void testSameWithSameEntities() {

        assertTrue(dataRegistry.getSetSize('a') == 0);
        assertTrue(dataRegistry.getSetSize('h') == 0);

        BusinessEntity bEntityA = new BusinessEntity();
        bEntityA.setWebsite("http://www.google.com");
        bEntityA.setPhone("(885)333 33 33");
        bEntityA.setName("How are you doing?");

        BusinessEntity bEntityB = new BusinessEntity();
        bEntityB.setWebsite("http://www.google.com");
        bEntityB.setPhone("(885)333 33 33");
        bEntityB.setName("How are you doing?");

        assertTrue(dataRegistry.offer(bEntityA));
        assertTrue(!dataRegistry.replace(bEntityA, bEntityB));
        assertTrue(dataRegistry.getSetSize('h') == 1);
    }

    @Test
    public void testSameWithDifferentEntities() {

        BusinessEntity bEntityA = new BusinessEntity();
        bEntityA.setWebsite("http://www.google.com");
        bEntityA.setPhone("(885)333 33 33");
        bEntityA.setName("How are you doing?");

        BusinessEntity bEntityB = new BusinessEntity();
        bEntityB.setWebsite("http://www.google.com");
        bEntityB.setPhone("(885)333 33 33");
        bEntityB.setName("How are you doing?");

        BusinessEntity bEntityC = new BusinessEntity();
        bEntityC.setWebsite("");
        bEntityC.setPhone("(885)333 33 33");
        bEntityC.setName("How are you doing?");

        BusinessEntity bEntityD = new BusinessEntity();
        bEntityD.setWebsite("http://www.google.com");
        bEntityD.setPhone("(885)333 33 33");
        bEntityD.setName("How are you doing?");

        BusinessEntity bEntityE = new BusinessEntity();
        bEntityE.setWebsite("http://www.google.com");
        bEntityE.setPhone("(885)333 33 33");
        bEntityE.setName("How are you ");

        assertTrue(dataRegistry.offer(bEntityA));
        assertTrue(!dataRegistry.replace(bEntityA, bEntityB));
        assertTrue(!dataRegistry.replace(bEntityA, bEntityC));
        assertTrue(dataRegistry.replace(bEntityC, bEntityD));
        assertTrue(!dataRegistry.replace(bEntityC, bEntityE));
    }
}
