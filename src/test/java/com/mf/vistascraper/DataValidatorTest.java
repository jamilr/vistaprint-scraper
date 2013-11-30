package com.mf.vistascraper;

import com.mf.vistascraper.core.impl.DataValidator;
import com.mf.vistascraper.model.BusinessEntity;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/29/13
 * Time: 8:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataValidatorTest {


    @Test
    public void testValidate() {

        BusinessEntity bEntity = new BusinessEntity();
        bEntity.setName("Yahoo");
        bEntity.getWebsites().add("http://www.yahoo.com");
        bEntity.getPhone().add("(333)333 3333");

        assertTrue(DataValidator.validate(bEntity));
        assertTrue(bEntity.getWebsites().iterator().next().equals("http://www.yahoo.com"));
    }

    @Test
    public void testValidateIncorrect() {

        BusinessEntity bEntity = new BusinessEntity();
        bEntity.setName("Yahoo");
        bEntity.getWebsites().add("http://POST");
        bEntity.getPhone().add("(333)333 3333");

        assertTrue(!DataValidator.validate(bEntity));
        assertTrue(bEntity.getWebsites().iterator().next().equals(""));
        assertTrue(!bEntity.getWebsites().iterator().next().equals("http://POST"));
    }
}
