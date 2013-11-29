package com.mf.vistascraper.model;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/28/13
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class AbstractBusinessEntity
        implements IBusinessEntity {

    protected String name;
    protected String phone;
    protected String website;

    protected Email email;

    public AbstractBusinessEntity() {
        this.email = new Email();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Email getEmail(){
        return email;
    }

    public void setEmail(Email emailObj) {
        this.email = emailObj;
    }
}
