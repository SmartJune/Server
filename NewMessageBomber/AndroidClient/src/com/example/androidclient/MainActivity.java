package com.example.androidclient;

import java.io.*;
import java.net.*;
import android.support.v7.app.ActionBarActivity;
import android.os.*;
import com.example.androidclient.DataObject;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity{
	
	String string = null;
	boolean flag = false;
	ObjectInputStream ois = null;
	Socket s;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new Thread(){
			public void run(){
				try{
				s = new Socket("192.168.1.112",9999);
				
				}catch(Exception e){
					e.printStackTrace();
				}
				
				while(true){
			        	try {
			        		ois = new ObjectInputStream(s.getInputStream());
			        		DataObject dao = new DataObject();
			        		dao = (DataObject) ois.readObject();
			        		System.out.println(dao.getUrl());
						} catch (OptionalDataException e) {
							// TODO Auto-generated catch block
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		}.start();
		SystemClock.sleep(4000);
		Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
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
}
