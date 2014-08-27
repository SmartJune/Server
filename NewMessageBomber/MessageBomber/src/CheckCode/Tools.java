package CheckCode;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.Iterator;

import android.graphics.*;
import android.os.Environment;

public class Tools {
	
	public static int pixelConvert(int pixel) {
        int result = 0;
        //获取图像的 RGB 分量
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = (pixel) & 0xff;

        result = 0xff000000;	//黑色
        //黑色 或 白色
        int tmp = r * r + g * g + b * b;
        if (tmp > 3 * 128 * 128) {
            result += 0x00ffffff;	//白色
        }
        return result;
    }

 
    public static int getStart(int widthPosition, Bitmap image){
    	//找到一个字符的起点
    	for (int i = widthPosition; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
            	if(image.getPixel(i, j) == 0xff000000 ){
            		return i;
            	}
            }       
    	}	
    	return -1;
    }
    
    public static int getEnd(int widthPosition, Bitmap image){
    	//找到字符的终点
    	int count = 0;
    	for (int i = widthPosition; i < image.getWidth() ; i++) {
    		count = 0;
            for (int j = 0; j < image.getHeight(); j++) {
            	if(image.getPixel(i, j) == 0xffffffff ){
            		count++;
            		if(count == image.getHeight()){
            			count = 0;
            			return i;
            		}
            	}
            }       
    	}
    	return -1;
    }

	public static Bitmap[] getCheckCodes(Bitmap image, int x) {
        //开始切割
    	int numb = 0;
    	numb = x;
    	Bitmap checkCode[] = new Bitmap[numb];
        int array[] = new int[2*numb+1];
    	int height = image.getHeight();
        array[0] = 0;
        saveBitmap(image, "test.bmp");
        for(int i=0;i<numb;i++){
        	array[2*i+1] = Tools.getStart(array[2*i], image);
        	array[2*i+2] = Tools.getEnd(array[2*i+1], image);
        	System.out.println(array[2*i+1]+"  "+array[2*i+2]);
        	checkCode[i] = Bitmap.createBitmap(image, array[2*i+1], 0, array[2*i+2]-array[2*i+1], height);
        }
        return checkCode;
    }
	public static void saveBitmap(Bitmap bitmap,String picName) {; 
		File f = new File("/sdcard/", picName); 
		if (f.exists()) { 
		f.delete(); 
		} 
		try { 
		FileOutputStream out = new FileOutputStream(f); 
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, out); 
		out.flush(); 
		out.close();
		} catch (FileNotFoundException e) { 
		// TODO Auto-generated catch block 
		e.printStackTrace(); 
		} catch (IOException e) { 
		// TODO Auto-generated catch block 
		e.printStackTrace(); 
		} 
		}


	public static void copy(Bitmap bitmap2, Bitmap bitmap) {
		// TODO Auto-generated method stub
		for(int i = 0;i<bitmap.getHeight();i++){
			for(int j = 0;j<bitmap.getWidth();j++){
				bitmap2.setPixel(j, i, bitmap.getPixel(j, i));
			}
			
		}
	} 
}
