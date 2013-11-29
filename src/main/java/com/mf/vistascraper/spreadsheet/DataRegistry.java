package com.mf.vistascraper.spreadsheet;

import com.mf.vistascraper.model.BusinessEntity;
import com.mf.vistascraper.util.Constants;
import com.mf.vistascraper.util.Util;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/28/13
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */

public class DataRegistry {

    private static Logger logger = Logger.getLogger(DataRegistry.class);

    private Map<Character, Map<String, BusinessEntity>> dataRegistry =
            new HashMap<Character, Map<String, BusinessEntity>>();

    public DataRegistry() {

        for (Character ch: Constants.ALPHABET)
            dataRegistry.put(ch, new HashMap<String, BusinessEntity>());
    }

    public boolean offer(BusinessEntity businessEntity) {

        boolean toPut = true;

        if (businessEntity.getName().isEmpty()) return false;

        char firstChar = businessEntity.getName().toLowerCase().charAt(0);

        Map<String, BusinessEntity> subRegistry = dataRegistry.get(firstChar);

        if (subRegistry != null) {
            for (String key: subRegistry.keySet()) {
                if (Util.getWordsDistance(key, businessEntity.getName()) >= 0.7) {
                    BusinessEntity otherBEntity = subRegistry.get(key);
                    if (!replace(businessEntity, otherBEntity))
                        toPut = false;
                    break;
                }
            }
        }

        if (toPut)
            dataRegistry.get(firstChar).put(businessEntity.getName(), businessEntity);

        return toPut;
    }

    public boolean replace(BusinessEntity businessEntityA, BusinessEntity businessEntityB) {

        boolean replace = false;

        if (Util.getWordsDistance(businessEntityA.getPhone(),businessEntityB.getPhone()) < 0.9) {
            if (!Util.validPhone(businessEntityA.getPhone()) && Util.validPhone(businessEntityB.getPhone())) {
                businessEntityA.setPhone(businessEntityB.getPhone());
                replace = true;
            }
        }

        if (Util.getWordsDistance(businessEntityA.getWebsite(), businessEntityB.getWebsite()) < 0.9) {
            if (!Util.validURL(businessEntityA.getWebsite()) && Util.validURL(businessEntityB.getWebsite())) {
                businessEntityA.setWebsite(businessEntityB.getWebsite());
                replace = true;
            }
        }

        if (businessEntityA.getName().length() < businessEntityB.getName().length())
            businessEntityA.setName(businessEntityB.getName());

        return replace;
    }

    public int getSetSize(Character ch) {
        return dataRegistry.get(ch).size();
    }
}
