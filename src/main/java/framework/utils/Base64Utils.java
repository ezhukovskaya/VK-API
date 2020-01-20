package framework.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Base64Utils {
    private static final Logger log = Logger.getLogger(Base64Utils.class);

    public static String getBase64EncodedImageByURL(String imageURL) {
        log.info(String.format("Converting JPG from %s to Base64", imageURL));
        try {
            InputStream is = new URL(imageURL).openStream();
            byte[] bytes = IOUtils.toByteArray(is);
            return Base64.encodeBase64String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBase64EncodedImage(String imageSrc) {
        log.info(String.format("Converting JPG %s to Base64", imageSrc));
        try {
            File file = new File(imageSrc);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
