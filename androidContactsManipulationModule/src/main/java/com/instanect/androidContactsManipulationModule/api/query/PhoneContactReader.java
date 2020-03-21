package com.instanect.androidContactsManipulationModule.api.query;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.extractor.main.PhoneContactExtractorMain;
import com.instanect.androidContactsManipulationModule.structs.PhoneContactCompleteObject;
import com.instanect.androidContactsManipulationModule.structs.accountType.PhoneContactAccountType;
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactUserData;

import java.util.ArrayList;

public class PhoneContactReader {


    private PhoneContactCursor phoneContactCursor;
    private PhoneContactExtractorMain phoneContactExtractorMain;
    private Context context;

    public PhoneContactReader(
            PhoneContactCursor phoneContactCursor,
            PhoneContactExtractorMain phoneContactExtractorMain,
            Context context
    ) {
        this.phoneContactCursor = phoneContactCursor;
        this.phoneContactExtractorMain = phoneContactExtractorMain;
        this.context = context;
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

    public ArrayList<PhoneContactUserData> getAllUserDataForAccount(
            PhoneContactAccountType phoneContactAccountType) {
        String whereName = ContactsContract.Data.MIMETYPE + " = ? AND "
                + ContactsContract.RawContacts.ACCOUNT_TYPE + " = ? AND "
                + ContactsContract.RawContacts.ACCOUNT_NAME + " = ?";
        String[] whereNameParams = new String[]{
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
                phoneContactAccountType.getAccountType(),
                phoneContactAccountType.getAccountName()};


        Cursor cursor = context.getContentResolver()
                .query(
                        ContactsContract.Data.CONTENT_URI,
                        null,
                        whereName,
                        whereNameParams,
                        null
                );

        if (cursor != null && cursor.getCount() > 0) {


            cursor.close();
        }

        return null;
    }
}