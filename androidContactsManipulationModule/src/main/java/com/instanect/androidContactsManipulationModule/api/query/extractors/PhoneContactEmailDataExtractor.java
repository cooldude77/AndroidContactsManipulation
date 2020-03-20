package com.instanect.androidContactsManipulationModule.api.query.extractors;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactEmailData;

import java.util.ArrayList;

public class PhoneContactEmailDataExtractor implements PhoneContactArrayListDataExtractorInterface {


    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;
    private ContentResolver contentResolver;

    public PhoneContactEmailDataExtractor(
            PhoneContactSegmentProvider phoneContactSegmentProvider,
            PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider,
            ContentResolver contentResolver) {
        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public ArrayList<PhoneContactEmailData> extract(int rawContactId) {

        ArrayList<PhoneContactEmailData> phoneContactEmailDataArrayList
                = phoneContactArrayListSegmentProvider.newInstance(PhoneContactEmailData.class);

        // get email and type
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.RAW_CONTACT_ID + " = ?",
                new String[]{String.valueOf(rawContactId)}, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                PhoneContactEmailData phoneContactEmailData =
                        (PhoneContactEmailData)
                                phoneContactSegmentProvider.newInstance(PhoneContactEmailData.class);
                // This would allow you get several email addresses
                // if the email addresses were stored in an array

                int emailContactId = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email._ID)));

                String email = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                int emailType = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)));

                phoneContactEmailData.setIdAndroid(emailContactId);
                phoneContactEmailData.setEmail(email);
                phoneContactEmailData.setEmailType(emailType);

                phoneContactEmailDataArrayList.add(phoneContactEmailData);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return phoneContactEmailDataArrayList;
    }
}