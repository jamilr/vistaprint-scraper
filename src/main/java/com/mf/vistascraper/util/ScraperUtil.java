package com.mf.vistascraper.util;

import com.mf.vistascraper.model.*;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/17/13
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */

public class ScraperUtil {

    private static Logger logger = Logger.getLogger(ScraperUtil.class);

    public static Document loadHTMLDocument(String url, String what, String where, String pageIdxStr) {

        Document document = null;
        try {

            Connection connection = Jsoup.connect(url)
                .data("TextBoxWhat",  what)
                .data("TextBoxWhere", where);

            if (pageIdxStr == null)
                connection.data("CurrentPageField", "1");

            document = connection
                    .timeout(Util.covertStringToInt(PropertiesUtil.getInstance().getProperty("con-timeout")))
                    .userAgent("Mozilla")
                    .post();

        } catch(SocketTimeoutException socEx) {
            logger.error(socEx.getMessage(), socEx);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return document;
    }

    public static Document loadHTMLDocument(String url) {

        Document document = null;
        try {

            document = Jsoup.connect(url)
                    .timeout(Util.covertStringToInt(PropertiesUtil.getInstance().getProperty("con-timeout")))
                    .get();

        } catch (SocketTimeoutException socEx) {
            logger.error(socEx.getMessage());
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }

        return document;
    }

    public static Document loadHTMLDocumentUsingPOST(String url) {

        Document document = null;
        try {

            document = Jsoup.connect(url).timeout(Util.covertStringToInt(PropertiesUtil
                    .getInstance().getProperty("con-timeout")))
                    .post();

        } catch (SocketTimeoutException socEx) {
            logger.error(socEx.getMessage(), socEx);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }

        return document;
    }

    public static Element getElementWithText(Document doc, String text) {

        String parentSelectorStr = "div.control-header:contains(%s)";
        String childSelectorStr = "div.control-content";

        parentSelectorStr = String.format(parentSelectorStr, text);
        Elements parents = doc.select(parentSelectorStr).parents();

        Element parent;
        if (parents != null && !parents.isEmpty())
            parent = parents.first();
        else return null;

        return (parent != null) ?parent.select(childSelectorStr).first() :null;
    }

    public static Elements getBusinessEntityLink(Document document) {

        if (document == null) {
            logger.warn("Document object is null");
            return new Elements();
        }

        return document.select("a.list-business-name");
    }

    public static Elements getBasicInfo(Element el) {

        checkNotNull(el, "Document is null");
        return el.select("div.list-basic-info-panel");
    }

    public static Elements getContactDetails(Element el) {

        checkNotNull(el, "Document is null");
        return el.select("table.list-contact-info-table");
    }

    public static Elements getBusinessEntityList(Document document) {

        if (document == null) {
            logger.warn("Document object is null");
            return new Elements();
        }

        return document.select("table.list-table");
    }

    public static Integer extractPageCount(Document doc) {

        if (doc == null) return 0;

        int pageCount = 0;
        String docText = doc.html();

        int bodyOnLoadIdx = docText.indexOf("BodyOnLoad");
        int moveToPageIdx = docText.indexOf("moveToPage");
        if (bodyOnLoadIdx< 0 || moveToPageIdx < 0 && (bodyOnLoadIdx >= moveToPageIdx))
            return 0;

        docText = docText.substring(bodyOnLoadIdx, moveToPageIdx).trim();
        docText = docText.replaceAll("[^\\d]+", " ").trim();

        String[] numStr = docText.split(" ");
        if (numStr.length == 1)
            pageCount = Integer.parseInt(docText);

        if (pageCount == 0)
            pageCount = 1;

        return pageCount;
    }

    public static AbstractBusinessEntity extractBusinessEntity(Element els) {

        BusinessEntity businessEntity = new BusinessEntity();

        Elements result;
        String title, phone, website, imgURL;

        Elements basicInfoEls = ScraperUtil.getBasicInfo(els);
        if (basicInfoEls.isEmpty())
            return NullBusinessEntity.getInstance();

        result = basicInfoEls.select("a");

        title = "";
        if (result != null)
            title = result.first().attr("title");

        title = Util.removeNonAlphaSymbols(title);

        Elements contactInfoEls = ScraperUtil.getContactDetails(els);
        if (contactInfoEls.isEmpty())
            return NullBusinessEntity.getInstance();

        phone = getValue(contactInfoEls,    "span:contains(Phone:)");
        website = getLinkValue(contactInfoEls, "span:contains(Website:)");
        imgURL = getImgURL(contactInfoEls,  "span:contains(Email:)");

        Email email = new Email();
        email.setEmailURL(imgURL);

        businessEntity.setName(title);
        businessEntity.getPhone().add(phone);
        businessEntity.getWebsites().add(website);
        businessEntity.setEmail(email);

        return businessEntity;
    }

    public static String getValue(Elements els, String selectorPath) {

        Element parent;
        Elements result, parents;
        String value = "";
        result = els.select(selectorPath);
        if (result != null) {
            parents = result.parents();
            if (parents != null) {
                parent = parents.first();
                value = parent.ownText();
            }
        }

        return value;
    }

    public static String getLinkValue(Elements els, String selectorPath) {

        Element parent;
        Elements result, parents;
        String value = "";
        result = els.select(selectorPath);
        if (result != null) {
            parents = result.parents();
            if (parents != null) {
                parent = parents.first();
                Elements links = parent.select("a");
                if (!links.isEmpty()) {
                    Element link = links.first();
                    value = link.ownText();
                }
            }
        }

        return value;
    }

    public static String getImgURL(Elements els, String selectorPath) {

        Elements result, parents;
        Element parent;
        String imgURL = "";

        result = els.select(selectorPath);
        if (result != null) {
            parents = result.parents();
            if (!parents.isEmpty()) {

                parent = parents.first();
                Elements imgs = parent.select("img");

                if (!imgs.isEmpty()) {
                    Element imgEl = imgs.first();
                    if (imgEl != null) {
                        imgURL = imgEl.attr("abs:src");
                        if (imgURL == null)
                            logger.error("Image URL is null");

                    }
                }
            }
        }

        return imgURL;
    }
}
