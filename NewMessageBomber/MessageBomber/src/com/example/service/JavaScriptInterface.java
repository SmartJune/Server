package com.example.service;

import java.util.regex.*;

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

        String testreg = "[^a-zA-Z\\s]";
        Pattern matchsip = Pattern.compile(testreg);
        Matcher mp = matchsip.matcher(url);
        url = mp.replaceAll("");
        
        url+="succeed";
        
		MobclickAgent.onEvent(context, url);
		System.out.println("这是JS运行成功的标签"+url);
	}
}
