package CheckCode;

import java.awt.image.BufferedImage;

import android.graphics.Bitmap;

public class Filter {

    public static void blackAndWhiteFilter(Bitmap image) {
        if (image == null) {
            return;
        }
        //循环 把每个像素点变成黑或者白
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                image.setPixel(j, i, Tools.pixelConvert(image.getPixel(j, i)));
            }
        }
    }

    public static void dotFilter(Bitmap image) {
        if (image == null) {
            return;
        }
        //如果周围都是白色，说明是噪点
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (i > 0 && j > 0 && i < (image.getHeight() - 1) && j < (image.getWidth() - 1)) {
                    if (image.getPixel(j, i) == 0xff000000) {
                        if (image.getPixel(j - 1, i) == 0xffffffff
                            && image.getPixel(j - 1, i - 1) == 0xffffffff
                            && image.getPixel(j, i - 1) == 0xffffffff
                            && image.getPixel(j + 1, i) == 0xffffffff
                            && image.getPixel(j + 1, i + 1) == 0xffffffff
                            && image.getPixel(j, i + 1) == 0xffffffff) {
                            image.setPixel(j, i, 0xffffffff);
                        }
                    }
                }
            }
        }
    }
}
