package com.example.all.at.one;

import java.util.ArrayList;

import com.example.all.at.one.Contacts.Customlist;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Messages extends ListActivity{

	ArrayList<String> list=new ArrayList<String>();
	ArrayList<String> data_list=new ArrayList<String>();
	ArrayList<String> data_list1=new ArrayList<String>();
	ArrayList<String> from_list=new ArrayList<String>();
	int count=0;
	//String from;
	ListView lv;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.message);
		lv=getListView();
		
		Bundle b=getIntent().getExtras();
		String key=b.getString("val_key");
		
		Uri uriSms = Uri.parse("content://sms/inbox");
		ContentResolver res=getContentResolver();
		Cursor c=res.query(uriSms, null,null,null,null); 
		
		try{
		if(c!=null){
			count=0;
			while(c.moveToNext()){
				
				String data=c.getString(c.getColumnIndex("body"));
				
				if(data.matches("(?i:.*"+key+".*)") || data.contains(key))
				{
			String	from=c.getString(c.getColumnIndex("address"));
				list.add(from+" \n"+data);
			//	String s=data.substring(0, 30);
			//	String s1=s+"...................";
				//Toast.makeText(getApplicationContext(), s1, 10).show();
				from_list.add(from);
			//	data_list1.add(s1);
				data_list.add(data);
				count++;
				
				}
				
				
				
			}

		
		}
		}
		catch(Exception e){}
			
	
		
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
		
		if(count==0){
			list.add("");
			from_list.add("No Results Found");
			
		}
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent itt=new Intent(Messages.this,Read_sms.class);
				itt.putExtra("val1", from_list.get(arg2));
				itt.putExtra("val2", data_list.get(arg2));
				startActivity(itt);
				
			}
		});
		
	}
	
	@Override
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
			return list.size();
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
				row=inf.inflate(R.layout.custom_xml_2, arg2,false);
				 
				 								}
			
			data=(TextView)row.findViewById(R.id.data);
			from=(TextView)row.findViewById(R.id.from);
			

			from.setText(from_list.get(arg0));
			data.setText(data_list.get(arg0));
			}catch(Exception e){}
			return row;
		}
		
	}
	
	
}
