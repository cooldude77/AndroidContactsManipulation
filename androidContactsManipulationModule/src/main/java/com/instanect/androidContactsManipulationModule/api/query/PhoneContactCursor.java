package com.instanect.androidContactsManipulationModule.api.query;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

public class PhoneContactCursor {
    private ContentResolver contentResolver;

    public PhoneContactCursor(ContentResolver contentResolver) {

        this.contentResolver = contentResolver;
    }

    public Cursor getCursor() {

        return contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

    }

    public Cursor getCursor(String selectionStatement, String[] selectionArgs) {

        return contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, selectionStatement, selectionArgs, null);

    }
}
