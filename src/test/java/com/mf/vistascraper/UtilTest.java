package com.mf.vistascraper;

import com.mf.vistascraper.util.Util;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/28/13
 * Time: 7:03 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UtilTest {

    @Test
    public void testRemoveNonAlphaSymbols() {

        String source = "*DivaLicious Dymonds";
        System.out.println(Util.removeNonAlphaSymbols(source));
        assertEquals("DivaLicious Dymonds", Util.removeNonAlphaSymbols(source));
    }

    @Test
    public void testValidateURL() {
        String url = "http://www.google.com";
        assertTrue(Util.validURL(url));
    }

    @Test
    public void testValidateWrongURL() {
        String url = "http://POST";
        assertTrue(!Util.validURL(url));
    }

    @Test
    public void testNGramDistance() {

        String wordA = "Jamil Rzayev";
        String wordB = "Jamil k Rzayev";
        System.out.println(Util.getWordsDistance(wordA, wordB));
    }

    @Test
    public void testNGramDistance2() {

        String wordA = "Usborne Books and More-Nancy Falter, Educational Consultant";
        String wordB = "Usborne Books-Nancy Falter";
        System.out.println(Util.getWordsDistance(wordA, wordB));
    }

    @Test
    public void testNGramDistanceWithSameWords() {

        String wordA = "OmniOptimalHealth";
        String wordB = "OmniOptimalHealth";
        System.out.println(Util.getWordsDistance(wordA, wordB));
    }
}
