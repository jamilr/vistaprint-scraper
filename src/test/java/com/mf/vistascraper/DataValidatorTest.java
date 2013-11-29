package com.mf.vistascraper;

import com.mf.vistascraper.core.impl.DataValidator;
import com.mf.vistascraper.model.BusinessEntity;
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
        bEntity.setWebsite("http://www.yahoo.com");
        bEntity.setPhone("(333)333 3333");

        assertTrue(DataValidator.validate(bEntity));
        assertTrue(bEntity.getWebsite().equals("http://www.yahoo.com"));
    }

    @Test
    public void testValidateIncorrect() {

        BusinessEntity bEntity = new BusinessEntity();
        bEntity.setName("Yahoo");
        bEntity.setWebsite("http://POST");
        bEntity.setPhone("(333)333 3333");

        assertTrue(!DataValidator.validate(bEntity));
        assertTrue(bEntity.getWebsite().equals(""));
        assertTrue(!bEntity.getWebsite().equals("http://POST"));
    }
}
