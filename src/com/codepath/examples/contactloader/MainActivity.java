package com.codepath.examples.contactloader;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
	ArrayList<Contact> listContacts;
	ListView lvContacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listContacts = new ContactFetcher(this).fetchAll();
		lvContacts = (ListView) findViewById(R.id.lvContacts);
		ContactsAdapter adapterContacts = new ContactsAdapter(this, listContacts);
		lvContacts.setAdapter(adapterContacts);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
