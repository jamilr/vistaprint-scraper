package com.mf.vistascraper;

import com.mf.vistascraper.model.BusinessEntity;
import com.mf.vistascraper.spreadsheet.DataRegistry;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
        bEntityA.getWebsites().add("http://www.google.com");
        bEntityA.getPhone().add("(912) 988 - 3112");
        bEntityA.setName("How are you doing?");

        BusinessEntity bEntityB = new BusinessEntity();
        bEntityB.getWebsites().add("http://www.google.com");
        bEntityB.getWebsites().add("(912) 988 - 3112");
        bEntityB.setName("How are you doing?");

        dataRegistry.update(bEntityA, bEntityB);
        assertEquals(1, bEntityA.getPhone().size());
    }

    @Test
    public void testSameWithDifferentEntities() {

        BusinessEntity bEntityA = new BusinessEntity();
        bEntityA.getWebsites().add("http://www.google.com");
        bEntityA.getPhone().add("(912) 988 - 3112");
        bEntityA.setName("How are you doing?");

        BusinessEntity bEntityB = new BusinessEntity();
        bEntityB.getWebsites().add("http://www.yahoo.com");
        bEntityB.getPhone().add("(912) 988 - 3112");
        bEntityB.setName("How are you doing?");

        BusinessEntity bEntityC = new BusinessEntity();
        bEntityC.getWebsites().add("");
        bEntityC.getPhone().add("(912) 988 - 3113");
        bEntityC.setName("How are you doing?");

        BusinessEntity bEntityD = new BusinessEntity();
        bEntityD.getWebsites().add("http://www.google.com");
        bEntityD.getPhone().add("");
        bEntityD.setName("How are you doing?");

        BusinessEntity bEntityE = new BusinessEntity();
        bEntityE.getWebsites().add("http://www.google.com");
        bEntityE.getPhone().add("(912) 988 - 3112");
        bEntityE.setName("How are you ");

        dataRegistry.update(bEntityA, bEntityB);
        assertEquals(2, bEntityA.getWebsites().size());

        dataRegistry.update(bEntityA, bEntityC);
        assertEquals(2, bEntityA.getPhone().size());

        dataRegistry.update(bEntityA, bEntityD);
        assertEquals(2, bEntityA.getPhone().size());
    }
}
