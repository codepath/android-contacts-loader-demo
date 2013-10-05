package com.codepath.examples.contactloader;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.RawContacts;
import android.support.v4.content.CursorLoader;

// new ContactFetcher(this).fetchAll();
public class ContactFetcher {
	private Context context;

	public ContactFetcher(Context c) {
		this.context = c;
	}

	public ArrayList<Contact> fetchAll() {
		ArrayList<Contact> listContacts = new ArrayList<Contact>();
		CursorLoader cursorLoader = new CursorLoader(context, RawContacts.CONTENT_URI, 
				null, // the columns to retrieve (all)
				null, // the selection criteria (none)
				null, // the selection args (none)
				null // the sort order (default)
		);

		Cursor c = cursorLoader.loadInBackground();
		if (c.moveToFirst()) {
			do {
				Contact contact = loadContactData(c);
				listContacts.add(contact);
			} while (c.moveToNext());
		}
		c.close();
		return listContacts;
	}

	private Contact loadContactData(Cursor c) {
		// Get Contact ID
		int idIndex = c.getColumnIndex(ContactsContract.Contacts._ID);
		String contactId = c.getString(idIndex);
		// Get Contact Name
		int nameIndex = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
		String contactDisplayName = c.getString(nameIndex);
		Contact contact = new Contact(contactId, contactDisplayName);
		fetchContactNumbers(c, contact);
		fetchContactEmails(c, contact);
		return contact;
	}

	public void fetchContactNumbers(Cursor cursor, Contact contact) {
		// Get numbers
		final String[] numberProjection = new String[] { Phone.NUMBER, Phone.TYPE, };

		Cursor phone = new CursorLoader(context, Phone.CONTENT_URI, numberProjection,
				RawContacts.CONTACT_ID + "= ?", new String[] { String.valueOf(contact.id) }, null)
				.loadInBackground();

		if (phone.moveToFirst()) {
			final int contactNumberColumnIndex = phone.getColumnIndex(Phone.NUMBER);
			final int contactTypeColumnIndex = phone.getColumnIndex(Phone.TYPE);

			while (!phone.isAfterLast()) {
				final String number = phone.getString(contactNumberColumnIndex);
				final int type = phone.getInt(contactTypeColumnIndex);
				String customLabel = "Custom";
				CharSequence phoneType = ContactsContract.CommonDataKinds.Phone.getTypeLabel(
						context.getResources(), type, customLabel);
				contact.addNumber(number, phoneType.toString());
				phone.moveToNext();
			}

		}
		phone.close();
	}

	public void fetchContactEmails(Cursor cursor, Contact contact) {
		// Get email
		final String[] emailProjection = new String[] { Email.DATA, Email.TYPE };

		Cursor email = new CursorLoader(context, Email.CONTENT_URI, emailProjection,
				RawContacts.CONTACT_ID + "= ?", new String[] { String.valueOf(contact.id) }, null)
				.loadInBackground();

		if (email.moveToFirst()) {
			final int contactEmailColumnIndex = email.getColumnIndex(Email.DATA);
			final int contactTypeColumnIndex = email.getColumnIndex(Email.TYPE);

			while (!email.isAfterLast()) {
				final String address = email.getString(contactEmailColumnIndex);
				final int type = email.getInt(contactTypeColumnIndex);
				String customLabel = "Custom";
				CharSequence emailType = ContactsContract.CommonDataKinds.Email.getTypeLabel(
						context.getResources(), type, customLabel);
				contact.addEmail(address, emailType.toString());
				email.moveToNext();
			}

		}

		email.close();
	}
}
