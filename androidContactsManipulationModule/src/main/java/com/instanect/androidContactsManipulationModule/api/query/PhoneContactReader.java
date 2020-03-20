package com.instanect.androidContactsManipulationModule.api.query;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.PhoneContactExtractorMain;
import com.instanect.androidContactsManipulationModule.structs.PhoneContactCompleteObject;

public class PhoneContactReader {


    private PhoneContactCursor phoneContactCursor;
    private PhoneContactExtractorMain phoneContactExtractorMain;

    public PhoneContactReader(
            PhoneContactCursor phoneContactCursor,
            PhoneContactExtractorMain phoneContactExtractorMain
    ) {
        this.phoneContactCursor = phoneContactCursor;
        this.phoneContactExtractorMain = phoneContactExtractorMain;
    }

    public PhoneContactCompleteObject getComplete(int rawId) {


      Cursor cursor = phoneContactCursor.getCursor(rawId);


        // do nor delete
        //contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
        //              null, ContactsContract.Contacts.DISPLAY_NAME+" like ?",
        //            new String[]{"%deep krishna%"}, null);


        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            return phoneContactExtractorMain.getPhoneContactObject(cursor);

        }
        return null;
    }

}