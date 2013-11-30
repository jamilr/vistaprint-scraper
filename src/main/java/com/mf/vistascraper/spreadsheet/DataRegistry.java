package com.mf.vistascraper.spreadsheet;

import com.mf.vistascraper.model.BusinessEntity;
import com.mf.vistascraper.util.Constants;
import com.mf.vistascraper.util.Util;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
            new TreeMap<Character, Map<String, BusinessEntity>>();

    public DataRegistry() {

        for (Character ch: Constants.ALPHABET)
            dataRegistry.put(ch, new HashMap<String, BusinessEntity>());
    }

    public boolean offer(BusinessEntity newBEntity) {

        boolean isNew = true;

        if (newBEntity.getName().isEmpty()) return false;

        Character firstChar = newBEntity.getName().toLowerCase().charAt(0);

        Map<String, BusinessEntity> subRegistry = dataRegistry.get(firstChar);

        float distance;
        BusinessEntity existingBEntity;
        if (subRegistry != null) {
            for (String key: subRegistry.keySet()) {

//                if (newBEntityName.charAt(1) > key.charAt(1)) {
//                    isNew = false;
//                    break;
//                }
                distance = 1 - Util.getWordsDistance(key, newBEntity.getName());

                if (distance <= 0.5) {

                    existingBEntity = subRegistry.get(key);

                    if (!update(existingBEntity, newBEntity)) {
                        isNew = false;
                        break;
                    }

                    if (existingBEntity.getName().length() < newBEntity.getName().length()) {
                        subRegistry.remove(key);
                        existingBEntity.setName(newBEntity.getName());
                    }

                    copy(newBEntity, existingBEntity);
                    break;
                }
            }
        }

        if (isNew)
            subRegistry.put(newBEntity.getName(), newBEntity);

        return isNew;
    }

    private void copy(BusinessEntity entityA, BusinessEntity entityB) {
        entityA.setName(entityB.getName());
        entityA.setEmail(entityB.getEmail());
        entityA.setPhones(entityB.getPhone());
        entityA.setWrbsites(entityB.getWebsites());
        entityA.setWorkBookRowIdx(entityB.getWorkBookRowIdx());
    }

    public boolean update(BusinessEntity originalEntity, BusinessEntity newEntity) {

        boolean updated = false;

        for (String phone : newEntity.getPhone()) {
            if (Util.validPhone(phone))
                if (!originalEntity.getPhone().contains(phone)) {
                    originalEntity.getPhone().add(phone);
                    updated = true;
            }
        }

        for (String webSite: newEntity.getWebsites()) {
            if (Util.validURL(webSite))
                if (!originalEntity.getWebsites().contains(webSite)) {
                    originalEntity.getWebsites().add(webSite);
                    updated = true;
                }
        }

        return updated;
    }

    public int getSetSize(Character ch) {
        return dataRegistry.get(ch).size();
    }
}
