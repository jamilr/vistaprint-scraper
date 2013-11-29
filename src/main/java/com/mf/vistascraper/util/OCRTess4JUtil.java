package com.mf.vistascraper.util;

import com.asprise.util.ocr.OCR;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/17/13
 * Time: 8:03 PM
 * To change this template use File | Settings | File Templates.
 */

public class OCRTess4JUtil {

    public static String extractTextFromImageURL(String img_url) {

        String text = "";
        try {

            BufferedImage image = ImageIO.read(new File("data\\img\\ocr.gif"));


            OCR.setLibraryPath("D:\\temp\\asprise.dll");
            // recognizes both characters and barcodes
            String s = new OCR().recognizeCharacters(image);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return text;
    }
}
