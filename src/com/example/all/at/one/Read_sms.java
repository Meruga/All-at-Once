package com.example.all.at.one;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Read_sms extends Activity implements OnInitListener {
	
	TextView from,data;
	Button speak;
	TextToSpeech ts;
	String num;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle b=getIntent().getExtras();
		String from_val=b.getString("val1");
		String data_val=b.getString("val2");
		setContentView(R.layout.calllogs);
	from=(TextView)findViewById(R.id.from);
	data=(TextView)findViewById(R.id.data);
	speak=(Button)findViewById(R.id.mic);
	
	from.setText(from_val);
	data.setText(data_val);
	num=from_val;
	
	 Intent it=new Intent(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
     startActivityForResult(it, 101);
     
     speak.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			MediaPlayer mp=MediaPlayer.create(Read_sms.this, R.raw.ting);
			mp.start();
			String textt=(String) data.getText(); 
			ts.setPitch(10);
			ts.setSpeechRate(-1);
			
			ts.speak(textt, TextToSpeech.QUEUE_FLUSH, null);
			
		}
	});
	
	}

	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	// TODO Auto-generated method stub
	    	if(requestCode==101)
	    	{
	    		ts=new TextToSpeech(this,this);
	    		
	    	}else
	    	{
	    		 Intent it=new Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
	    	        startActivityForResult(it, 101);
	    	}
	    	
	    	
	    	super.onActivityResult(requestCode, resultCode, data);
	    }
	    
	    
		public void onInit(int status) {
			// TODO Auto-generated method stub
			
		}
		
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
			
			MenuInflater mi=getMenuInflater();
			mi.inflate(R.menu.me, menu);
			
			return super.onCreateOptionsMenu(menu);
			
		}
	
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
		
			switch(item.getItemId()){
			
			case R.id.reply:
			//	Toast.makeText(Read_sms.this, from, 10).show();
				Uri uri = Uri.parse("smsto:"+num);   
		    	Intent it = new Intent(Intent.ACTION_SENDTO, uri);   
		    	it.putExtra("sms_body", "");   
		    	startActivity(it); 
				
				return true;
						
			case R.id.call:
				
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+num));
				startActivity(intent);
				return true;
			
			}
			
			return super.onOptionsItemSelected(item);
		}
}
