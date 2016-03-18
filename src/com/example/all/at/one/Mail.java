package com.example.all.at.one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Mail extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle b=getIntent().getExtras();
	  	String key=b.getString("val_key");
	 
	try
		{
	  		Intent mailClient = new Intent(Intent.ACTION_SEARCH);
	  		mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
	  		mailClient.putExtra("query", key);
	  		startActivity(mailClient);
		}
	  	
	catch(Exception e){
		
	}
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		Mail.this.finish();
		
		
	}
	
}
