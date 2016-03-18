package com.example.all.at.one;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class Web extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle b=getIntent().getExtras();
	  	String key=b.getString("val_key");
	
	
	  	Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, key);
        startActivity(intent);
	}
	
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		Web.this.finish();
		
		
	}

}
