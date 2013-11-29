package com.mf.vistascraper.spreadsheet;

import com.mf.vistascraper.util.Constants;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/27/13
 * Time: 6:48 PM
 * To change this template use File | Settings | File Templates.
 */

public class ExcelWorkBook {

    private static Logger logger = Logger.getLogger(ExcelWorkBook.class);

    private String filePathStr;

    private Workbook workbook;
    private Sheet sheet;

    public ExcelWorkBook(String filePath) {

        this.filePathStr = filePath;
        this.workbook = new HSSFWorkbook();
        this.sheet = workbook.createSheet(Constants.SHEET_NAME);

        this.sheet.setDefaultRowHeight((short)500);
        for (int i=0; i<Constants.SHEET_COLS_SET.size(); i++)
            this.sheet.autoSizeColumn(i);
    }

    public synchronized Row addRow(int rowIdx) {

        checkArgument(rowIdx >= 0, "Row Index must be positive", rowIdx);
        return sheet.createRow(rowIdx);
    }

    public synchronized void addValueToRow(Row row, int cellIdx, WorkBookValueType valueType, Object value) {

        checkNotNull(row, "Row is null");
        checkNotNull(value, "Value is null");
        ExcelWorkBookHelper.addValueToRow(row, cellIdx, valueType, value);
    }

    public synchronized void addImage(int col, int row, byte[] imageData) {
        ExcelWorkBookHelper.addImage(workbook, sheet, col, row, imageData);
    }

    public synchronized void addSheet(String sheetName) {
        ExcelWorkBookHelper.addSheet(workbook, sheetName);
    }

    public synchronized void save() {

        try {

            FileOutputStream output = new FileOutputStream( new File(filePathStr));
            workbook.write(output);
            output.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
