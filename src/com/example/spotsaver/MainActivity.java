package com.example.spotsaver;

import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	ArrayAdapter<UserLocation> adapter;
	Button addButton;
	ArrayList<UserLocation> listOfLocations = new ArrayList<UserLocation>();
	ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addButton = (Button) findViewById(R.id.activBN);
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveCurrentSpot();

			}
		});

		adapter = new ArrayAdapter<UserLocation>(this,
				android.R.layout.simple_list_item_1, listOfLocations);

		lv = (ListView) findViewById(R.id.listView);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				
				UserLocation locl = listOfLocations.get(pos);
				
				String uri = "google.navigation:ll=%f,%f";
				Intent navIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(uri, locl.getLat(), locl.getLong())));
				startActivity(navIntent);
			}

		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {
                // TODO Auto-generated method stub
            	
        		 listOfLocations.remove(pos);
        		 adapter.notifyDataSetChanged();

                return true;
            }
        }); 
		
		writeListOut();
		
	}

	// Save Current Spot
	private void saveCurrentSpot() {
		AlertDialog.Builder cityAlert = new AlertDialog.Builder(this);

		cityAlert.setTitle("Title");
		cityAlert.setMessage("Location Label:");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		cityAlert.setView(input);

		cityAlert.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String inputLable = input.getText().toString();

						LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
						Location loc = lm
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						double latitude = loc.getLatitude();
						double longitude = loc.getLongitude();

						UserLocation ul = new UserLocation(inputLable,
								longitude, latitude);
						listOfLocations.add(ul);

					}
				});

		cityAlert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});
		cityAlert.create();
		cityAlert.show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void writeListOut(){
		String FILENAME = "data_file";
		String s = "test string";
		try {
			FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(s.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
