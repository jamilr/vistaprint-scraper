package com.mf.vistascraper.core;

import com.mf.vistascraper.model.IBusinessEntity;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/17/13
 * Time: 8:14 AM
 * To change this template use File | Settings | File Templates.
 */


public interface IScraperService<T extends IBusinessEntity> {

    void scrape();
}
