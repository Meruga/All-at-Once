package com.example.all.at.one;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Images extends Activity{

	ListView ls;
	int count=0;
	ImageView im;
    Uri uri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    String name,image;
    ArrayList<String> data=new ArrayList<String>();
    ArrayList<String> image_list=new ArrayList<String>();
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		String[] projection =  new String[]{MediaStore.Images.Media.DISPLAY_NAME,MediaStore.Images.Media.DATA};
        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        im=(ImageView)findViewById(R.id.imageView1);
        //ls=getListView();
        ContentResolver res=getContentResolver();
    	
    	Cursor cr=res.query(uri, projection, null, null, null);
    	Bundle b=getIntent().getExtras();
    	String key=b.getString("val_key");
        
    	if(cr!=null){
    		count=0;
    		while(cr.moveToNext()){
    			 name=cr.getString(cr.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
    			 
    			 if(name.matches("(?i:.*"+key+".*)")){
    			 image=cr.getString(cr.getColumnIndex(MediaStore.Images.Media.DATA));
    			data.add(name);
    			image_list.add(image);
    			
    			 }
    			
    			 
    		}
    		
    		
    		}
    	
    	
    	
    	gallery.setAdapter(new Imageadapter(this));
    
    //	ls.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,data));
   
    	
    	gallery.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
		/*	String val=image_list.get(arg2);
				Intent it=new Intent(getApplicationContext(),Image_view.class);
				it.putExtra("val_key", val);
				startActivity(it);*/
								
				Intent intent = new Intent();  
				intent.setAction(android.content.Intent.ACTION_VIEW);  
				File file = new File(image_list.get(arg2));  
				intent.setDataAndType(Uri.fromFile(file), "image/png");  
				startActivity(intent);
				
				
			}
		});
    	
    
	
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
	
		
	}


	public class Imageadapter extends BaseAdapter{

    	int mGalleryItemBackground;
       	private Context mcon;
		public Imageadapter(Context c) {
			
			mcon=c;
		TypedArray attr = mcon.obtainStyledAttributes(R.styleable.HelloGallery);
		        mGalleryItemBackground = attr.getResourceId(
		                R.styleable.HelloGallery_android_galleryItemBackground, 0);
		        attr.recycle();
			
			

		}

		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		public View getView(int arg0, View convertview, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ImageView imageview;
			
			
				imageview=new ImageView(mcon);
				String	img=image_list.get(arg0);
				
				Drawable dr=Drawable.createFromPath(img);
				
				imageview.setImageDrawable(dr);
			
				imageview.setLayoutParams(new Gallery.LayoutParams(230,230));
				
				imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		
			
			return imageview;
		}
    	
    }
}
