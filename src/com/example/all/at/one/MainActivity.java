package com.example.all.at.one;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends Activity {
  Button search;
  AutoCompleteTextView key;
  String word;
  String word1;
  Spinner spin;
  ProgressDialog pd;
  SQLiteDatabase db=null;
  String tablename="count";
  ArrayList<String> list=new ArrayList<String>();
    public void onCreate(Bundle savedInstanceState) {
    	
    	db=openOrCreateDatabase("sample",MODE_WORLD_WRITEABLE, null);
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tablename+"(name TEXT PRIMARY KEY)");
    	Cursor c1=null;
		c1=db.rawQuery("SELECT * FROM "+tablename,null );
		
		if(c1!=null){
			while(c1.moveToNext()){
			
				String list_name=c1.getString(c1.getColumnIndex("name"));
				
					list.add(list_name);
				
			}
			
		}
    	
    	super.onCreate(savedInstanceState);
    	
        setContentView(R.layout.search);
        search=(Button)findViewById(R.id.button1);
        key=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        spin=(Spinner)findViewById(R.id.spinner1);
        
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinnerAdapter);
        spinnerAdapter.add("Phone");
        spinnerAdapter.add("Gmail");
        spinnerAdapter.add("Google");
        spinnerAdapter.add("Maps");
        spinnerAdapter.notifyDataSetChanged();
        
        pd=new ProgressDialog(MainActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Just Wait.......");
        pd.setCancelable(false);
        
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				word1=arg0.getItemAtPosition(arg2).toString();
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
       // list.add("sss");
    key.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list ));
        search.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
		
				
			String key_value1=key.getText().toString();
			
			insert(key_value1);
				//pd.show();
				
				if(word1=="Phone" && key.getText().toString().length()>0){
					word=key.getText().toString();
					Intent it=new Intent(MainActivity.this,Tab.class);
					it.putExtra("val", word);
					startActivity(it);
				}
				
			 if(word1=="Gmail" && key.getText().toString().length()>0){
					word=key.getText().toString();
					 Intent i=new Intent(MainActivity.this,Mail.class);
				        i.putExtra("val_key",word);
				       startActivity(i);
				}
				
			 if(word1=="Google" && key.getText().toString().length()>0){
					
					word=key.getText().toString();
					 Intent i1=new Intent(MainActivity.this,Web.class);
				        i1.putExtra("val_key",word);
				       startActivity(i1);
				       
				}
				
			 if(word1=="Maps" && key.getText().toString().length()>0){
					
					word=key.getText().toString();
					 Intent i1=new Intent(MainActivity.this,Maps.class);
				        i1.putExtra("val_key",word);
				       startActivity(i1);
				       
				}
				
				if (key.getText().toString().length()==0){
					Toast.makeText(MainActivity.this,"enter a key word" , 100).show();
					pd.cancel();
				}
			
				
				
			}
		});
    	
        
    }
    
    protected void insert(String string) {
		// TODO Auto-generated method stub
		
    	try{	
		String insert="INSERT INTO "+tablename+" VALUES('"+string+"')";
		db.execSQL(insert);
	//	Toast.makeText(MainActivity.this, "inserted", 10).show();
	}catch(Exception e){}
	}

	@Override
    	protected void onResume() {
    		// TODO Auto-generated method stub
    		super.onResume();
    	
    		pd.cancel();
    
    }
	
	@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
			
		MenuInflater mi= getMenuInflater();
		mi.inflate(R.menu.m1, menu);
		return super.onCreateOptionsMenu(menu);
			
			
		}
	
	@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
		
			if(item.getItemId()==R.id.clear_search){
				try{
				db.delete(""+tablename, null, null);
				
				}catch(Exception e){}
				
				}
			Intent ii=new Intent(this,MainActivity.class);
			startActivity(ii);
			
			return super.onOptionsItemSelected(item);
		}
}
