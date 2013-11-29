package com.mf.vistascraper.spreadsheet;

import com.mf.vistascraper.model.BusinessEntity;
import com.mf.vistascraper.model.Email;
import com.mf.vistascraper.util.Constants;
import org.apache.poi.ss.usermodel.Row;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/19/13
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */

public class DataWriter {

    private ExcelWorkBook workBook;
    private DataRegistry dataRegistry;

    private int curRowIdx;
    private boolean saveOnOffer;
    private boolean headerExists;

    public DataWriter(String filePath, boolean saveOnOffer) {

        this.headerExists = false;
        this.saveOnOffer = saveOnOffer;
        this.curRowIdx = 0;

        this.workBook = new ExcelWorkBook(filePath);
        this.dataRegistry = new DataRegistry();
    }

    public synchronized void addHeader() {

        if (headerExists) return;

        Row row = workBook.addRow(curRowIdx);
        int idx = 0;

        for (String keywords: Constants.SHEET_COLS_SET) {
            workBook.addValueToRow(row, idx, WorkBookValueType.STRING, keywords);
            idx++;
        }
        headerExists = true;
        curRowIdx++;

        save();
    }

    public synchronized boolean offer(BusinessEntity businessEntity) {

        if (!dataRegistry.offer(businessEntity))
            return false;

        if (saveOnOffer)
            addRow(businessEntity);

        return true;
    }

    public void addRow(BusinessEntity businessEntity) {

        String name = businessEntity.getName();
        String phone = businessEntity.getPhone();
        String webSite = businessEntity.getWebsite();

        Email email = businessEntity.getEmail();
        byte[] imgData = email.getData();

        Row row = workBook.addRow(curRowIdx);

        workBook.addValueToRow(row, 0, WorkBookValueType.STRING, name);
        workBook.addValueToRow(row, 1, WorkBookValueType.STRING, phone);
        workBook.addValueToRow(row, 2, WorkBookValueType.STRING, webSite);

        if (imgData != null)
            workBook.addImage(3, curRowIdx, imgData);

        curRowIdx++;

        save();
    }

    public void save() {
        workBook.save();
    }
}
