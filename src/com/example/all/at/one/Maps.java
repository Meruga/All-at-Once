package com.example.all.at.one;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Maps extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		Bundle b=getIntent().getExtras();
	  	String key=b.getString("val_key");
		
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+key));
		startActivity(intent);
		
		
	}
	
	
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		Maps.this.finish();
		
		
	}
	
}
