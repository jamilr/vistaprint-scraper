package com.mf.vistascraper;

import com.mf.vistascraper.model.BusinessEntity;
import com.mf.vistascraper.model.Email;
import com.mf.vistascraper.spreadsheet.DataWriter;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/30/13
 * Time: 7:41 AM
 * To change this template use File | Settings | File Templates.
 */

public class DataWriterTest {

    @Test
    public void testUpdate() {

        String filePath = "data\\data_2.xls";
        DataWriter dataWriter = new DataWriter(filePath, true);

        Email email = new Email();
        email.setEmail("john@gmail.com");

        BusinessEntity bEntityA = new BusinessEntity();
        bEntityA.setName("John-Moon");
        bEntityA.setEmail(email);
        bEntityA.getWebsites().add("http://www.john.com");
        bEntityA.getPhone().add("(973) 289 - 4224");

        BusinessEntity bEntityB = new BusinessEntity();
        bEntityB.setName("John Moon Jr. ");
        bEntityB.setEmail(email);
        bEntityB.getWebsites().add("http://www.drjohnmoon.com");
        bEntityB.getPhone().add("(973) 289 - 4324");

        dataWriter.offer(bEntityA);
        dataWriter.offer(bEntityB);
    }
}
