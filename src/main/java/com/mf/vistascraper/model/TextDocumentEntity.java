package com.mf.vistascraper.model;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/19/13
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */

public class TextDocumentEntity implements IBusinessEntity {

    private String keywords;

    private String categories;

    public TextDocumentEntity() {

    }

    public TextDocumentEntity(String keys, String cats) {
        this.keywords = keys;
        this.categories = cats;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
