package com.example.all.at.one;

import java.text.MessageFormat;
import java.util.ArrayList;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Contacts extends ListActivity{
	
	ProgressDialog pd;
	ListView lv;
	int count=0;
	String nu;
	String number,name1,photo;
	ArrayList<String> list=new ArrayList<String>();
	ArrayList<String> name_list=new ArrayList<String>();
	ArrayList<String> number_list=new ArrayList<String>();
	ArrayList<String> photo_list=new ArrayList<String>();
	ArrayList<String> id_list=new ArrayList<String>();
	ArrayList<String> uri_list=new ArrayList<String>();
	Uri uri=ContactsContract.Contacts.CONTENT_URI;
	Uri u1=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	private int REL_SWIPE_MIN_DISTANCE; 
    private int REL_SWIPE_MAX_OFF_PATH;
    private int REL_SWIPE_THRESHOLD_VELOCITY;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	String[] projection =  new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME,
										ContactsContract.Contacts.HAS_PHONE_NUMBER,ContactsContract.Contacts.PHOTO_ID};

	String key;
	protected void onCreate(Bundle savedInstanceState) {
		
	 super.onCreate(savedInstanceState);

	 DisplayMetrics dm = getResources().getDisplayMetrics();
     REL_SWIPE_MIN_DISTANCE = (int)(120.0f * dm.densityDpi / 160.0f + 0.5); 
     REL_SWIPE_MAX_OFF_PATH = (int)(250.0f * dm.densityDpi / 160.0f + 0.5);
     REL_SWIPE_THRESHOLD_VELOCITY = (int)(200.0f * dm.densityDpi / 160.0f + 0.5);
	
	lv=getListView();

	Bundle b=getIntent().getExtras();
	 key=b.getString("val_key");

	 new Mytask().execute();

	
	
	

final GestureDetector gestureDetector = new GestureDetector(new MyGestureDetector());
View.OnTouchListener gestureListener = new View.OnTouchListener() {
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event); 
    }};
lv.setOnTouchListener(gestureListener);

lv.setOnItemClickListener(new OnItemClickListener() {

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), id_list.get(arg2), 50).show();
		
        
	}
});
registerForContextMenu(lv);

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
	
	
	private void myOnItemClick(int position) {
        String str = MessageFormat.format("Item clicked = {0,number}", position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        
       //  pos=position;
    }

    private void onLTRFling(int pos) {

    	Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number_list.get(pos)));
		startActivity(intent);
    	
    }

    private void onRTLFling(int pos) {
    	
    	Uri uri = Uri.parse("smsto:"+number_list.get(pos));   
    	Intent it = new Intent(Intent.ACTION_SENDTO, uri);   
    	it.putExtra("sms_body", "");   
    	startActivity(it);  
    
    }
    
    class MyGestureDetector extends SimpleOnGestureListener{ 

        // Detect a single-click and call my own handler.
        @Override 
        public boolean onSingleTapUp(MotionEvent e) {
            ListView lv = getListView();
            int pos = lv.pointToPosition((int)e.getX(), (int)e.getY());
            myOnItemClick(pos);
            return false;
        }

        @Override 
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) { 
            if (Math.abs(e1.getY() - e2.getY()) > REL_SWIPE_MAX_OFF_PATH) 
                return false; 
            if(e1.getX() - e2.getX() > REL_SWIPE_MIN_DISTANCE && 
                Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) { 
            	int pos = lv.pointToPosition((int)e1.getX(), (int)e1.getY());
                onRTLFling(pos); 
            }  else if (e2.getX() - e1.getX() > REL_SWIPE_MIN_DISTANCE && 
                Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) { 
            	int pos = lv.pointToPosition((int)e1.getX(), (int)e1.getY());
                onLTRFling(pos); 
            } 
            return false; 
        } 

    }
    
    
public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Choose an option");
		menu.setHeaderIcon(android.R.drawable.ic_menu_more);
		MenuInflater mi=getMenuInflater();
		mi.inflate(R.menu.activity_main, menu);
		
	
	}
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    int index = info.position;
	    if(item.getItemId()==R.id.send_contact){
	    	Intent it = new Intent(Intent.ACTION_VIEW);   
	    	it.putExtra("sms_body", name_list.get(index)+","+number_list.get(index));   
	    	it.setType("vnd.android-dir/mms-sms");   
	    	startActivity(it);
	    }
		
	    if(item.getItemId()==R.id.add){
	    	
	    	Intent intent = new Intent(Intent.ACTION_INSERT);
	    	intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
	    	intent.putExtra(ContactsContract.Intents.Insert.NAME, "");
	    	intent.putExtra(ContactsContract.Intents.Insert.PHONE, "");
	    	startActivity(intent);

	    	
	    }

	    
	    if(item.getItemId()==R.id.call){
	    	Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number_list.get(index)));
			startActivity(intent);
	    }
	    
	    
	    if(item.getItemId()==R.id.message){
	    	
	    	Uri uri = Uri.parse("smsto:"+number_list.get(index));   
	    	Intent it = new Intent(Intent.ACTION_SENDTO, uri);   
	    	it.putExtra("sms_body", "");   
	    	startActivity(it); 
	    	
	    }
	    
	    if(item.getItemId()==R.id.edit){
	    	
	    	
	    	Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI,id_list.get(index));
	        Intent contViewIntent = new Intent(Intent.ACTION_VIEW,uri);
	        startActivity(contViewIntent);
	    }
	    
		return super.onContextItemSelected(item);
	}
	
	class Customlist extends BaseAdapter{
		
		TextView name,phone;
		ImageView view;
		
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
				row=inf.inflate(R.layout.custom_xml, arg2,false);
				 
				 								}
			
			name=(TextView)row.findViewById(R.id.name);
			phone=(TextView)row.findViewById(R.id.phone);
			view=(ImageView)findViewById(R.id.imageView1);

		//	Toast.makeText(Contacts.this, count, 40).show();
		
			name.setText(name_list.get(arg0));
			phone.setText(number_list.get(arg0));
			// Drawable img = Drawable.createFromPath(photo_list.get(arg0));
				
			 //   view.setImageDrawable(img);
		}catch(Exception e){}
			return row;
		}
		
	}
	
	
	
		class Mytask extends AsyncTask<Void, Void, Void>{

		
			
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				
				 pd=new ProgressDialog(Contacts.this);
				pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pd.setMessage("Give it a second Buddy.....");
				pd.setCancelable(false);
				pd.show();
				
			}
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				ContentResolver res=getContentResolver();
				
				Cursor cr=res.query(uri, projection, null, null, ContactsContract.Contacts.DISPLAY_NAME
			            + " COLLATE LOCALIZED ASC");
				
				cr.moveToFirst();
				
				try{
					if(cr!=null){
						count=0;
						while(cr.moveToNext()){
							
							String id = cr.getString(cr.getColumnIndex(ContactsContract.Contacts._ID));
							
							int ph=Integer.parseInt(cr.getString(cr.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
							
							String name=cr.getString(cr.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
							photo=cr.getString(cr.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
							
							if(ph>0){
								
								ContentResolver res1=getContentResolver();
								Cursor cr1=res1.query(u1, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id, null, null);
								
									while(cr1.moveToNext())
									
									{	
										number=cr1.getString(cr1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
										photo=cr.getString(cr.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));	
										
													
									if(name.matches("(?i:.*"+key+".*)"))
									{
										id_list.add(id);
										list.add(name+" "+number);
										number_list.add(number);
										name_list.add(name);
										photo_list.add(photo);
										count++;
									
									}
									
									
									
									
									}
									
									cr1.close();
										}
										
								}
							cr.close();
							
							
							
						}
					
					
					
					}
					
					catch(Exception e)
					{}

				
			//	Toast.makeText(Contacts.this, ""+count, 10).show();
				
				if(count==0){
					name_list.add("No Results Found");
					list.add("empty");
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				
				pd.cancel();
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
				
				Customlist ls=new Customlist();

			lv.setAdapter(ls);
				
			}
		}
	
	
}
