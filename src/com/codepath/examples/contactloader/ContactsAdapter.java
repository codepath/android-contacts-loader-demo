package com.codepath.examples.contactloader;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<Contact> {

	public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
		super(context, 0, contacts);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item
		Contact contact = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.adapter_contact_item, null);
		}
		// Populate the data into the template view using the data object
		TextView tvName = (TextView) view.findViewById(R.id.tvName);
		TextView tvEmail = (TextView) view.findViewById(R.id.tvEmail);
		TextView tvPhone = (TextView) view.findViewById(R.id.tvPhone);
		tvName.setText(contact.name);
		if (contact.emails.get(0) != null) {
			tvEmail.setText(contact.emails.get(0).address);
		}
		if (contact.numbers.get(0) != null) {
			tvPhone.setText(contact.numbers.get(0).number);
		}
		return view;
	}

}
