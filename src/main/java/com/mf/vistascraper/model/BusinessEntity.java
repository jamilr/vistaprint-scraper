package com.mf.vistascraper.model;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/17/13
 * Time: 8:17 AM
 * To change this template use File | Settings | File Templates.
 */

public class BusinessEntity extends AbstractBusinessEntity
        implements IBusinessEntity {

    public BusinessEntity() {
        super();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof BusinessEntity))
            return false;

        BusinessEntity otherBusinessEntity = (BusinessEntity)obj;
        if (!this.getName().equalsIgnoreCase(otherBusinessEntity.getName()))
            return false;

        return true;
    }

    @Override
    public String toString() {

        return new StringBuilder(name)
                .append(",").append(phones.toArray().toString())
                .append(",").append(websites.toArray().toString())
                .append(",").append(email)
                .append("\\n").toString();
    }
}
