package com.example.all.at.one;

import java.util.ArrayList;

import com.example.all.at.one.Contacts.Customlist;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Music extends ListActivity {
    /** Called when the activity is first created. */
    
	ListView lv;
	int count=0;
	ArrayList<String> audiolist=new ArrayList<String>();
	ArrayList<String> dataval=new ArrayList<String>();
	ArrayList<String> name_list=new ArrayList<String>();
	ArrayList<String> time_list=new ArrayList<String>();
	ArrayList<String> path_list=new ArrayList<String>();
	ArrayList<String> id_list=new ArrayList<String>();
	ContentResolver cr;
	String time,id;
	String data;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
      lv=getListView();
      Bundle b=getIntent().getExtras();
  	String key=b.getString("val_key");
        cr=getContentResolver();
     
       String projection[]={MediaStore.Audio.Media.DISPLAY_NAME,
    		   				MediaStore.Audio.Media.DATA,
    		   				MediaStore.Audio.Media.SIZE,
    		   				MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media._ID,
    		   				MediaStore.Audio.Media.ALBUM
    		   				}; 
        
        
   Cursor c=
	   cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection, null, null, MediaStore.Audio.Media.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
       
    
        if(c!=null)
        	count=0;
        {
        	while(c.moveToNext())
        	{
        		String name=c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
        		
        		
        		if(name.matches("(?i:.*"+key+".*)"))	{
        			id=c.getString(c.getColumnIndex(MediaStore.Audio.Media._ID));
        			
        		data=c.getString(1);
        		dataval.add(data);        		
        		int  size=c.getInt(2);
        		int duration=c.getInt(3);
        		
        		
        		int convertedvalue=(size/1024);
        		int seconds= (int)((duration/1000)%60);
        		int min=(int)((duration/1000)/60);
        		int hours=(int)((duration/1000)/3600);
        		
        		if(hours<=0)
        		{
        			time=min+":"+seconds;
        		}
        			
        		
        		else
        		{
        			time=hours+":"+min+":"+seconds;
        		}
        		audiolist.add(name+"  :"+data+"  :"+convertedvalue+"  :"+time);
        		name_list.add(name);
        		time_list.add(time);
        		path_list.add(data);
        		id_list.add(id);
        		count++;
        		
        		}
        		
        		        	
        	}
        	
        	
        	 }
        
        if(count==0){
        	
        	name_list.add("No Result Found");
        	audiolist.add("empty");
        }
       
        
        AnimationSet set = new AnimationSet(true);

	    Animation animation = new AlphaAnimation(0.0f, 1.0f);
	    animation.setDuration(100);
	    set.addAnimation(animation);
	animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF,
			 		0.0f,Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f);
	        animation.setDuration(200);
	        set.addAnimation(animation);


	        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
	      
      
        Customlist ls=new Customlist();
       
       
       lv.setAdapter(ls);
       
       lv.setLayoutAnimation(controller);
       
       lv.setOnItemClickListener(new OnItemClickListener(){

		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			// TODO Auto-generated method stub

			Intent it = new Intent(Intent.ACTION_VIEW);
			Uri uri = Uri.parse("file://"+path_list.get(pos));
			it.setDataAndType(uri, "audio/mp3");
			startActivity(it);

		}}); 
    }
	
	
	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		AnimationSet set = new AnimationSet(true);

	    Animation animation = new AlphaAnimation(0.0f, 1.0f);
	    animation.setDuration(100);
	    set.addAnimation(animation);
	animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF,
			 		0.0f,Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f);
	        animation.setDuration(200);
	        set.addAnimation(animation);


	        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
	        lv.setLayoutAnimation(controller);
	        
	       
	}
	
	
class Customlist extends BaseAdapter{
		
		TextView name,time,path;

		public int getCount() {
			// TODO Auto-generated method stub
			return audiolist.size();
		}

		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View row=arg1;
			try{
			if(row==null){
				LayoutInflater inf=getLayoutInflater();
				row=inf.inflate(R.layout.custom_xml_3, arg2,false);
				 
				 								}
			
			name=(TextView)row.findViewById(R.id.song_name);
			time=(TextView)row.findViewById(R.id.time);
			path=(TextView)row.findViewById(R.id.path);

			name.setText(name_list.get(arg0));
			time.setText(time_list.get(arg0));
		//	path.setText(path_list.get(arg0));
			}catch(Exception e){}
			return row;
		}
		
	}
	
	
}