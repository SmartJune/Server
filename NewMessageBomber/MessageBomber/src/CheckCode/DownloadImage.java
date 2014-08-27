package CheckCode;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.messagebomber.MainActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class DownloadImage extends Thread{

	public Bitmap downFiletoDecive(String url) {

		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getImageStream(url));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("下载失败了");
		} 
		return bitmap;
	}

	public InputStream getImageStream(String path) throws Exception{  
		URL url = new URL(path);  
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
		conn.setConnectTimeout(5 * 1000);  
		conn.setRequestMethod("GET");  
		if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
			return conn.getInputStream();  
		}  
		return null;  
	} 
}