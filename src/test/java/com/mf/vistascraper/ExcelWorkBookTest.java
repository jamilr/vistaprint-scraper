package com.mf.vistascraper;

import com.mf.vistascraper.spreadsheet.ExcelWorkBook;
import com.mf.vistascraper.spreadsheet.ExcelWorkBookHelper;
import com.mf.vistascraper.spreadsheet.WorkBookValueType;
import com.mf.vistascraper.util.ImageUtil;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/27/13
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */

public class ExcelWorkBookTest {


    @Test
    public void testHSSSpreadSheet() {

        String imgFilePath = "data\\img\\image_1.png";
        String filePath = "data\\spread.xls";
        ExcelWorkBook workBook = new ExcelWorkBook(filePath);

        Row row1 = workBook.addRow(0);
        workBook.addValueToRow(row1, 0, WorkBookValueType.STRING, "#");
        workBook.addValueToRow(row1, 1, WorkBookValueType.STRING, "Name");

        Row row2 = workBook.addRow(1);
        workBook.addValueToRow(row2, 0, WorkBookValueType.NUM, 1);
        workBook.addValueToRow(row2, 1, WorkBookValueType.STRING, "Yahoo");

        byte[] imgData = ImageUtil.readImgData(imgFilePath);
        workBook.addImage(2, 1, imgData);

        workBook.save();
    }

}
