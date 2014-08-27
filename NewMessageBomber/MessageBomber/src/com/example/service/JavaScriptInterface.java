package com.example.service;

import com.umeng.analytics.MobclickAgent;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class JavaScriptInterface {
	Context context;
	public JavaScriptInterface(Context context){
		this.context = context;
	}
	@JavascriptInterface
	public void used(String url){
		MobclickAgent.onEvent(context, url);
		System.out.println(url+"正常运行了");
	}
}
