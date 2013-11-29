package com.mf.vistascraper.model;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/28/13
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */

public class Email {

    private String emailURL;

    private String email;

    private byte[] data;

    public Email() {
        this.email = "";
        this.emailURL = "";
        this.data = null;
    }

    public String getEmailURL() {
        return emailURL;
    }

    public void setEmailURL(String emailURL) {
        this.emailURL = emailURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof Email))
            return false;

        Email emailObj = (Email)obj;
        if (!this.email.equals(emailObj.email))
            return false;

        if (!this.emailURL.equals(emailObj.emailURL))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return emailURL.concat(email);
    }
}
