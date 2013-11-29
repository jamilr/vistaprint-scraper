package com.mf.vistascraper.model;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/28/13
 * Time: 9:47 AM
 * To change this template use File | Settings | File Templates.
 */

public class NullBusinessEntity extends AbstractBusinessEntity
        implements IBusinessEntity {

    private static NullBusinessEntity nullBusinessEntity = new NullBusinessEntity();

    public static NullBusinessEntity getInstance() {
        return nullBusinessEntity;
    }
}
