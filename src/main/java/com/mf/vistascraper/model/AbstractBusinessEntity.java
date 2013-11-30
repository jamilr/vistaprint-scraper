package com.mf.vistascraper.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    protected Set<String> phones;
    protected Set<String> websites;
    protected int workBookRowIdx;

    protected Email email;

    public AbstractBusinessEntity() {
        this.email = new Email();
        this.phones = new HashSet<String>();
        this.websites = new HashSet<String>();
        this.workBookRowIdx = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Email getEmail(){
        return email;
    }

    public void setEmail(Email emailObj) {
        this.email = emailObj;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public Set<String> getPhone(){
        return phones;
    }

    public void setWrbsites(Set<String> websites) {
        this.websites = websites;
    }

    public Set<String> getWebsites(){
        return websites;
    }

    public int getWorkBookRowIdx(){
        return workBookRowIdx;
    }

    public void setWorkBookRowIdx(int rowIdx) {
        this.workBookRowIdx = rowIdx;
    }
}
