package com.example.messagebomber;

import java.io.IOException;

import com.example.service.Bomber;
import com.example.service.JavaScriptInterface;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgentJSInterface;

import CheckCode.CheckCode;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;



@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
public class MainActivity extends ActionBarActivity{

	EditText editText;
	Button sendButton;
	WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
		AdManager.getInstance(this).init("abf202a2c78802a4", "003f39e7340fd8d3", false);
	
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		// 设置广告条的悬浮位置
		layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // 这里示例为右下角
		// 实例化广告条
		AdView adView = new AdView(this,
				AdSize.FIT_SCREEN);
		// 调用Activity的addContentView函数
		this.addContentView(adView, layoutParams);

		// 监听广告条接口
		adView.setAdListener(new AdViewListener() {

			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "广告条切换");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告成功");

			}

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告失败");
			}
		});
		SpotManager.getInstance(this)
				.loadSpotAds();
		// 设置展示超时时间，加载超时则不展示广告，默认0，代表不设置超时时间
		SpotManager.getInstance(this)
				.setSpotTimeout(5000);// 设置5秒
		SpotManager.getInstance(this)
				.setShowInterval(20);// 设置20秒的显示时间间隔
		
		
		editText = (EditText)findViewById(R.id.phoneNumber);
		sendButton = (Button)findViewById(R.id.button);
		sendButton.setOnClickListener(new ButtonClickListener());
		webView = (WebView)findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBlockNetworkImage(true);
		webView.addJavascriptInterface(new JavaScriptInterface(getApplicationContext()), "something");
		webView.setVisibility(View.INVISIBLE);
		Elua.show(this);
	}
	
	public void onEulaAgreedTo(){
		Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();
	}
	
	public void onBackPressed() {
		// 如果有需要，可以点击后退关闭插播广告。
		if (!SpotManager
				.getInstance(this)
				.disMiss(true)) {
			super.onBackPressed();
		}
	}

	@Override
	protected void onStop() {
		// 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
		SpotManager.getInstance(this)
				.disMiss(false);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		SpotManager.getInstance(this)
				.unregisterSceenReceiver();
		super.onDestroy();
	}

	
	private class ButtonClickListener implements View.OnClickListener{
			
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String phoneNumber = editText.getText().toString();
			Thread thread = new Thread(new Bomber(phoneNumber,webView,getApplicationContext()));
	//		CheckCode c = new CheckCode("http://et.hnair.com/huet/servlet/com.travelsky.web.huet.user.GenImage?1408518188891",4,getApplicationContext());
	//		c.check();
			thread.start();
			Toast.makeText(getApplicationContext(),getResources().getString(R.string.begin)+phoneNumber, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}
		
}
