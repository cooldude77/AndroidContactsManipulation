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
        // contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
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
        String whereName = ContactsContract.RawContacts.ACCOUNT_TYPE + " = ? AND "
                + ContactsContract.RawContacts.ACCOUNT_NAME + " = ?";
        String[] whereNameParams = new String[]{
                phoneContactAccountType.getAccountType(),
                phoneContactAccountType.getAccountName()};


        Cursor cursor = context.getContentResolver()
                .query(
                        ContactsContract.RawContacts.CONTENT_URI,
                        null,
                        whereName,
                        whereNameParams,
                        null
                );
        ArrayList<PhoneContactUserData> phoneContactUserDataArrayList = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {

            do {
                PhoneContactUserData phoneContactUserData = phoneContactExtractorMain
                        .getUserDataByContactRawId(cursor.getInt(cursor.getColumnIndex(
                                ContactsContract.RawContacts._ID))
                        );
                if (phoneContactUserData != null)
                    phoneContactUserDataArrayList.add(phoneContactUserData);

            } while (cursor.moveToNext());

            cursor.close();
        }

        return phoneContactUserDataArrayList;
    }
}