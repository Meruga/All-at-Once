package com.example.all.at.one;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Events extends ListActivity{
	
	ListView lv;
	ArrayList<String> alist1=new ArrayList<String>();
	ArrayList<String> event=new ArrayList<String>();
	ArrayList<String> description=new ArrayList<String>();
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		lv=getListView();
		 Bundle b=getIntent().getExtras();
	    	String key=b.getString("val_key");
	    //	Toast.makeText(getApplicationContext(), key, 10).show();
	    	
	    	
			final ContentResolver cr = this.getContentResolver();
	        try
	        {
	          Cursor  cursor = cr.query(Uri.parse("content://com.android.calendar/events"), new String[]{ "calendar_id", "title", "description", "dtstart", "dtend","_id", "eventLocation" }, null, null, null);         
	         

	            Log.i("Sample Activity", "Cursor size " +cursor.getCount());
	            String add = null;
	            cursor.moveToFirst();
	            String[] CalNames = new String[cursor.getCount()];
	            int[] CalIds = new int[cursor.getCount()];
	            for (int i = 0; i < CalNames.length; i++) 
	            {
	                CalIds[i] = cursor.getInt(0);
	             //   CalNames[i] = "Event"+cursor.getInt(0)+": \nTitle: "+ cursor.getString(1)+"\nDescription: "+cursor.getString(2)+"\nStart Date: "+new Date(cursor.getLong(3))+"\nEnd Date : "+new Date(cursor.getLong(4))+"\nLocation : "+cursor.getString(5);
	             
	                
	                
	                String event_name=cursor.getString(1);
	                String description_name=cursor.getString(2);
	                
	            String id=cursor.getString(cursor.getColumnIndex("_id"));
	             //   alist1.add(id);
	            //    alist.add(CalNames[i]);
	             	                
	              //  alist.add(CalNames[i]);
	                if(event_name.matches("(?i:.*"+key+".*)") || description_name.matches("(?i:.*"+key+".*)")){
	                	
	                event.add(event_name);
	                description.add(description_name);
	                alist1.add(id);
	                	
	                	
	                }
	                
	                if(add == null)
	                {
	                    add = CalNames[i];
	                }
	                else
	                {
	                    add += CalNames[i];
	                }           
	        //        ((TextView)findViewById(R.id.calendars)).setText(add);
	                Log.i("SAmple Reminder****", "events from calendar "+ add);
	                cursor.moveToNext();
	               }
	               cursor.close();
	            }
	            catch(Exception e)
	            {
	                e.printStackTrace();
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
	       
	      
	//   lv.setAdapter(new ArrayAdapter<String>(Events.this, android.R.layout.simple_expandable_list_item_1,event));
		        lv.setLayoutAnimation(controller);
	        
	    	
	    	lv.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					Intent intent = new Intent(Intent.ACTION_VIEW);
					//Android 2.2+
					intent.setData(Uri.parse("content://com.android.calendar/events/" + alist1.get(arg2)));  
					//Android 2.1 and below.
					//intent.setData(Uri.parse("content://calendar/events/" + String.valueOf(calendarEventID)));    
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					        | Intent.FLAG_ACTIVITY_SINGLE_TOP
					        | Intent.FLAG_ACTIVITY_CLEAR_TOP
					        | Intent.FLAG_ACTIVITY_NO_HISTORY
					        | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
					startActivity(intent);
					
				}
			});
	    	
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
		
		TextView from,data;

		public int getCount() {
			// TODO Auto-generated method stub
			return event.size();
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
			
			if(row==null){
				LayoutInflater inf=getLayoutInflater();
				row=inf.inflate(R.layout.custom_5, arg2,false);
				 
				 								}
			
			data=(TextView)row.findViewById(R.id.event_description);
			from=(TextView)row.findViewById(R.id.event_title);
			

			from.setText(event.get(arg0));
			data.setText(description.get(arg0));
			
			return row;
		}
		
	}
	

	
}
