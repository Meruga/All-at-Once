package com.example.all.at.one;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TabHost;
import android.widget.ViewSwitcher;

public class Tab extends TabActivity{
	
	String ss;
 ViewSwitcher switcher;

	private static final int REFRESH_SCREEN = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
			
		setContentView(R.layout.activity_main);
			
		
		
		Bundle b=getIntent().getExtras();
		ss=b.getString("val");
		
		Resources res=getResources();
    	TabHost host=getTabHost();
    	TabHost.TabSpec specify;
    	Intent it1,it2,it3,it4,it5,it6,it7;
      
    	it1=new Intent(this,Contacts.class);
        it1.putExtra("val_key",ss);
        specify=host.newTabSpec("contact").setIndicator(" CONTACTS ").setContent(it1);
        host.addTab(specify);
    
    
        it3=new Intent(this,Messages.class);
        it3.putExtra("val_key",ss);
        specify=host.newTabSpec("Messages").setIndicator(" MESSAGES ").setContent(it3);
        host.addTab(specify);
            
        it4=new Intent(this,Music.class);
        it4.putExtra("val_key",ss);
        specify=host.newTabSpec("music").setIndicator("  MUSIC  ").setContent(it4);
        host.addTab(specify);
    
        it2=new Intent(this,Video.class);
        it2.putExtra("val_key",ss);
        specify=host.newTabSpec("video").setIndicator("  VIDEO  ").setContent(it2);
        host.addTab(specify);
        
        it5=new Intent(this,Images.class);
        it5.putExtra("val_key",ss);
        specify=host.newTabSpec("images").setIndicator("  IMAGES  ").setContent(it5);
        host.addTab(specify);
	
      it2=new Intent(this,App.class);
         it2.putExtra("val_key",ss);
        specify=host.newTabSpec("apps").setIndicator("   APPS   ").setContent(it2);
        host.addTab(specify);
       
        
        it6=new Intent(this,Events.class);
        it6.putExtra("val_key",ss);
       specify=host.newTabSpec("event").setIndicator(" EVENTS ").setContent(it6);
       host.addTab(specify);
        
       
       it7=new Intent(this,Files_search.class);
       it7.putExtra("val_key",ss);
      specify=host.newTabSpec("files").setIndicator(" FILES ").setContent(it7);
      host.addTab(specify);
        
	
	}
	
}
