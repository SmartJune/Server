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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
public class MainActivity extends ActionBarActivity{

	EditText editText;
	Button sendButton;
	WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
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
