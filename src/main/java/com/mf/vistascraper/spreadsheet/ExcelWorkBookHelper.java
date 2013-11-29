package com.mf.vistascraper.spreadsheet;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/27/13
 * Time: 6:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExcelWorkBookHelper {

    private static Logger logger = Logger.getLogger(ExcelWorkBookHelper.class);

    public static void addValueToRow(Row row, int cellIdx, WorkBookValueType valueType, Object value) {

        switch (valueType) {
            case NUM: {
                Integer intValue = (Integer)value;
                row.createCell(cellIdx).setCellValue(intValue);
            }
            break;
            case STRING: {
                String strValue = (String)value;
                row.createCell(cellIdx).setCellValue(strValue);
            }
            break;
            default:
        }
    }


    public static void addImage(Workbook workbook, Sheet sheet, int col, int row, byte[] imageData) {

        checkNotNull(workbook,  "Workbook is null");
        checkNotNull(sheet,     "Sheet is null");

        int pictureIdx = workbook.addPicture(imageData, Workbook.PICTURE_TYPE_JPEG);

        CreationHelper helper = workbook.getCreationHelper();

        // Create the drawing patriarch.  This is the top level container for all shapes.
        Drawing drawing = sheet.createDrawingPatriarch();

        //add a picture shape
        ClientAnchor anchor = helper.createClientAnchor();

        //set top-left corner of the picture,
        //subsequent call of Picture#resize() will operate relative to it
        anchor.setCol1(col);
        anchor.setRow1(row);
        Picture pict = drawing.createPicture(anchor, pictureIdx);

        //auto-size picture relative to its top-left corner
        pict.resize();
    }

    public static Sheet addSheet(Workbook workbook, String sheetName) {
        checkNotNull(workbook, "Workbook is null");
        return workbook.createSheet(sheetName);
    }
}
