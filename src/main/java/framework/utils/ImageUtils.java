package framework.utils;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class ImageUtils {
    static final Logger LOG = Logger.getLogger(ImageUtils.class);

    public static boolean compareLocalAndURL(String filePath, String fileUrl) {
        LOG.info(String.format("Compares %s and %s by pixel", filePath, fileUrl));
        BufferedImage screen = null;
        try {
            screen = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int w = screen.getWidth();
        int h = screen.getHeight();
        int[][] screenArray = new int[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                screenArray[i][j] = screen.getRGB(i, j);
            }
        }
        BufferedImage screen2 = null;
        try {
            screen2 = ImageIO.read(new URL(fileUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int w2 = screen2.getWidth();
        int h2 = screen2.getHeight();
        int[][] screenArray2 = new int[w2][h2];
        for (int i = 0; i < w2; i++) {
            for (int j = 0; j < h2; j++) {
                screenArray2[i][j] = screen2.getRGB(i, j);
            }
        }
        return Arrays.deepEquals(screenArray, screenArray2);
    }

}
