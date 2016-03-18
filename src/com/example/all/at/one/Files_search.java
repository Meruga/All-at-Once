package com.example.all.at.one;

import java.io.File;
import java.util.ArrayList;

import com.example.all.at.one.Music.Customlist;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

public class Files_search extends ListActivity{
	
	ArrayList<String> filename=new ArrayList<String>();
	ArrayList<String> file_path=new ArrayList<String>();
	ListView lv;
	String key;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ListView lv=getListView();
	
		
		  Bundle b=getIntent().getExtras();
		  	 key=b.getString("val_key");
		  	
		  //	Toast.makeText(getApplicationContext(), key, 10).show();
		  	
		  	File sdcardObj=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
			
			
			
			
			listFiles(sdcardObj,filename,file_path);
			
		//	lv.setAdapter(new ArrayAdapter<String>(Files_search.this, android.R.layout.simple_expandable_list_item_1,filename));
		
	
			
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
		       
			
			lv.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					try
					{
					Intent myIntent = new Intent(android.content.Intent.ACTION_VIEW);
					File file = new File(file_path.get(arg2));
					String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
					String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
					myIntent.setDataAndType(Uri.fromFile(file),mimetype);
					startActivity(myIntent);
					}
					catch (Exception e)
					{
					// TODO: handle exception
					String data = e.getMessage();
					}
					
				}
			});
			
	}

	
	
	
	private void listFiles(File sdcard,ArrayList<String> filename,ArrayList<String> file_path) {
        if(sdcard.isDirectory()){
            File[] files = sdcard.listFiles();

                    try {
                        for (File f : files){
                        	if(f.getName().matches("(?i:.*"+key+".*)")){
                           if(!f.isDirectory()) {
                                if(f.getName().endsWith(".doc")||  f.getName().endsWith(".txt")|| f.getName().endsWith(".docx")||f.getName().endsWith(".rtf") || f.getName().endsWith(".ppt")) {
                                    // Log.d(" FILES",f.getName());
                                  filename.add(f.getName());
                                  file_path.add(f.getAbsolutePath());
                               }
                             }
                       	}
                           else {
                                this.listFiles(f,filename,file_path);
                            }
                    
                        //	}
                        }
              } catch (Exception e) {
                     // TODO Auto-generated catch block
                    //e.printStackTrace();
           } 
     }
	
	
	}
	
	
	
	
	
	
class Customlist extends BaseAdapter{
		
		TextView name,path;

		public int getCount() {
			// TODO Auto-generated method stub
			return filename.size();
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
				row=inf.inflate(R.layout.custom_3, arg2,false);
				 
				 								}
			
			name=(TextView)row.findViewById(R.id.file_name);
			path=(TextView)row.findViewById(R.id.file_path);

			name.setText(filename.get(arg0));
			path.setText(file_path.get(arg0));
		//	path.setText(path_list.get(arg0));
			
			return row;
		}
		
	}



/*protected void onResume() {
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

}*/
}

