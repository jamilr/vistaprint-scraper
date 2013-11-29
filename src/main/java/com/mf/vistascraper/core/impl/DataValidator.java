package com.mf.vistascraper.core.impl;

import com.mf.vistascraper.model.AbstractBusinessEntity;
import com.mf.vistascraper.util.ImageUtil;
import com.mf.vistascraper.util.Util;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/28/13
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */

public class DataValidator {

    public static boolean validate(AbstractBusinessEntity businessEntity) {

        if (businessEntity.getClass().equals(NullPointerException.class))
            return false;

        String webSite = businessEntity.getWebsite();
        String phone = businessEntity.getPhone();

        if (!phone.isEmpty())
            phone = Util.extractNumFromStr(phone);

        byte[] imgData = null;
        String imgURL = businessEntity.getEmail().getEmailURL();
        if (imgURL != null)
            imgData = ImageUtil.loadImage(imgURL);

        if (!validateWebSiteURL(webSite)) {
            webSite = "";
            businessEntity.setWebsite(webSite);
            if (imgData == null)
                return false;
        }

        businessEntity.setPhone(phone);
        businessEntity.getEmail().setData(imgData);

        return true;
    }

    private static boolean validateWebSiteURL(String webSite) {

        boolean valid = true;

        if (!webSite.isEmpty())
            webSite = Util.extractURLFromStr(webSite);

        if (!Util.validURL(webSite))
            valid = false;

        return valid;
    }
}
