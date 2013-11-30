package com.mf.vistascraper.core.impl;

import com.mf.vistascraper.model.AbstractBusinessEntity;
import com.mf.vistascraper.util.ImageUtil;
import com.mf.vistascraper.util.Util;

import java.util.Set;

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

        boolean valid = true;

        String webSite = businessEntity.getWebsites().iterator().next();
        String phone = businessEntity.getPhone().iterator().next();

        if (!phone.isEmpty())
            phone = Util.extractNumFromStr(phone);

        byte[] imgData = null;
        String imgURL = businessEntity.getEmail().getEmailURL();
        if (imgURL != null && Util.validURL(imgURL))
            imgData = ImageUtil.loadImage(imgURL);

        if (!validateWebSiteURL(webSite)) {
            webSite = "";
            if (imgData == null)
                valid = false;
        }

        businessEntity.getWebsites().clear();
        businessEntity.getWebsites().add(webSite);
        businessEntity.getPhone().clear();
        businessEntity.getPhone().add(phone);
        businessEntity.getEmail().setData(imgData);

        return valid;
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
