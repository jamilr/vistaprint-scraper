package com.mf.vistascraper.util;

import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/17/13
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */

public class ImageUtil {

    private static Logger logger = Logger.getLogger(ImageUtil.class);

    public static byte[] readImgData(String imgFilePath) {

        byte[] imgData = null;
        InputStream is;
        try {

            is = new FileInputStream(imgFilePath);
            imgData = IOUtils.toByteArray(is);
            is.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            return imgData;
        }
    }

    public static byte[] loadImage(String imgURL) {

        byte[] imgData = null;
        if (imgURL == null) {
            logger.error("Image URL is null");
            return imgData;
        }

        BufferedImage img;
        ByteArrayOutputStream byteArrayStream;
        try {

            img = ImageIO.read(new URL(imgURL));
            byteArrayStream = new ByteArrayOutputStream();
            ImageIO.write( img, "png", byteArrayStream);
            byteArrayStream.flush();

            imgData = byteArrayStream.toByteArray();
            byteArrayStream.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }

        return imgData;
    }

}
