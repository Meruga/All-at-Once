package com.example.all.at.one;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class App extends ListActivity{
	ProgressDialog pd;
	
	ArrayList<String> list=new ArrayList<String>();
	ArrayList<String> plist=new ArrayList<String>();
	ArrayList<String> app=new ArrayList<String>();
	ArrayList<String> version=new ArrayList<String>();
	ArrayList<String> number=new ArrayList<String>();
	ArrayList<Drawable> ic=new ArrayList<Drawable>();
	ListView lv;
	PackageManager pm;
	String key;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //List<PackageInfo> apps = getPackageManager().getInstalledPackages(0);
        lv=getListView();
        Bundle b=getIntent().getExtras();
     key=b.getString("val_key");
    	
     new Mytask().execute();
    	 
       
     lv.setOnItemClickListener(new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
			Intent LaunchApp = getPackageManager().getLaunchIntentForPackage(plist.get(arg2));
					startActivity( LaunchApp );
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
		
		TextView name;
		ImageView view;
		List<PackageInfo> apps = getPackageManager().getInstalledPackages(0);
		public int getCount() {
			// TODO Auto-generated method stub
			return app.size();
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
				row=inf.inflate(R.layout.custom_xml_4, arg2,false);
				 
				 								}
			
			name=(TextView)row.findViewById(R.id.appname);
			
			view=(ImageView)row.findViewById(R.id.app1);

		//	Toast.makeText(Contacts.this, count, 40).show();
			
			name.setText(app.get(arg0));
			Drawable dd=ic.get(arg0);
			view.setImageDrawable(dd);
			
			}
			catch(Exception e){}
			return row;
		
			
		}
		
	}
	

		class Mytask extends AsyncTask<Void, Void, Void>{

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				 pd=new ProgressDialog(App.this);
					pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					pd.setMessage("Loading takes time.....");
					pd.setCancelable(false);
					pd.show();
			}
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				 pm = App.this.getPackageManager();
				 Intent intent = new Intent(Intent.ACTION_MAIN, null);
		    	  intent.addCategory(Intent.CATEGORY_LAUNCHER);

		    	  List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.PERMISSION_GRANTED);
		    	  
		    	  
		    	  
		    	  for (ResolveInfo rInfo : list) {
		    		  // results.add(rInfo.activityInfo.applicationInfo.loadLabel(pm).toString());
		    		  		String appname=rInfo.activityInfo.applicationInfo.loadLabel(pm).toString();
		    		  
		    		  if(appname.matches("(?i:.*"+key+".*)")){
		    		  app.add(rInfo.activityInfo.applicationInfo.loadLabel(pm).toString());
		    		  	  plist.add(rInfo.activityInfo.applicationInfo.packageName);
		    			  
		    			  ic.add(rInfo.activityInfo.applicationInfo.loadIcon(pm));
		    			  
		    		  // Log.w("Installed Applications", rInfo.activityInfo.applicationInfo.loadLabel(pm).toString());
		    		  } 
		    		  
		    	  }
		        
				
				return null;
			}
			
			
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				 AnimationSet set = new AnimationSet(true);

				    Animation animation = new AlphaAnimation(0.0f, 1.0f);
				    animation.setDuration(100);
				    set.addAnimation(animation);
				animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF,
						 		0.0f,Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f);
				        animation.setDuration(200);
				        set.addAnimation(animation);


				        pd.cancel();
				        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
				        lv.setLayoutAnimation(controller);
				
				        Customlist ls=new Customlist();

				        lv.setAdapter(ls);	
				
				
			}
			
		}
	
}
