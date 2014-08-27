package CheckCode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.http.conn.params.ConnConnectionParamBean;

import com.example.messagebomber.R;
import android.R.integer;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.*;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
public class CheckCode {
	
	static DownloadImage di = new DownloadImage();
	Bitmap bitmap = null;
	String codeUrl = null;
	int codeNumber = 0;
	Context context = null;
	static AssetManager assetManager;
	public CheckCode(String codeUrl, int codeNumber, Context context){
		this.codeUrl = codeUrl;
		this.codeNumber = codeNumber;
		this.context = context;
		assetManager = context.getAssets();
	}
	
    public void check() {
    	Runnable runnable = new Runnable() {		
			public void run() {
				bitmap = di.downFiletoDecive(codeUrl);
				System.out.println("运行过");
			}
		};
		new Thread(runnable).start();
		SystemClock.sleep(4000);
		SystemClock.sleep(4000);
		SystemClock.sleep(4000);
		Tools.saveBitmap(bitmap, "firstDownload.bmp");
		Bitmap bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Tools.copy(bitmap2,bitmap);
		Tools.saveBitmap(bitmap2, "editable.bmp");
		Filter.blackAndWhiteFilter(bitmap2);
		Filter.dotFilter(bitmap2);
		Tools.saveBitmap(bitmap2, "afterChanged.bmp");
		compare(bitmap2,codeNumber);				
    }
    public static String compare(Bitmap image, int codeNumber){
         Bitmap checkCode[] = Tools.getCheckCodes(image,codeNumber);
        int count = 0;
        String code = "";
        for (int t = 0; t < codeNumber; t++) {
            boolean ckFlg = false;
            boolean flag = false;
            int num=-1;
            Bitmap testImage = null;
            for (int i = 0; i < 10; i++) {
                num=-1;
                ckFlg = true;
                flag = false;
                count = 0;
                String[] files = null;
                try {
					files = assetManager.list("test1");
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					System.out.println("无法打开目录");
				}
                try {
					testImage = BitmapFactory.decodeStream(assetManager.open("test1/"+i+".png"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("无法获取对比图");
				}
                	if(testImage==null){
                    continue;
                }
                for (int y = 0; y < checkCode[t].getHeight() && !flag; y++) {
                    for (int x = 0; x < checkCode[t].getWidth(); x++) {               	
                        int expRGB = Tools.pixelConvert(checkCode[t].getPixel(x, y));
                        int cmpRGB = 0;
                        try{
                        	cmpRGB = Tools.pixelConvert(testImage.getPixel(x, y));
                        }catch(Exception e){
                        //	System.out.println(e.getMessage());
                        }
                        if (expRGB != cmpRGB) {
							count++;
							if (count >= (checkCode[t].getWidth() * checkCode[t].getHeight() * 0.1)) {
								flag = true;
								ckFlg = false;
								break;
							}
						}
					}
                }
                if (ckFlg) {
                    num=i;
                    break;
                }
            }
            if (ckFlg) {
                code+=num;
                System.out.println("第"+(t+1)+"个数字是 "+num);
            }
        }
        System.out.println(code);
		return code;
    }
}
