package com.instanect.androidContactsManipulationModule.api.query.extractors;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactPhoneData;

import java.util.ArrayList;

public class PhoneContactPhoneDataExtractor implements PhoneContactArrayListDataExtractorInterface {


    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;
    private ContentResolver contentResolver;

    public PhoneContactPhoneDataExtractor(PhoneContactSegmentProvider phoneContactSegmentProvider,
                                          PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider,
                                          ContentResolver contentResolver) {
        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public ArrayList<PhoneContactPhoneData> extract(int id) {


        ArrayList<PhoneContactPhoneData> phoneContactPhoneDataArrayList
                = phoneContactArrayListSegmentProvider.newInstance(PhoneContactPhoneData.class);

        // get the phone number
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{String.valueOf(id)}, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                PhoneContactPhoneData phoneContactPhoneData =
                        (PhoneContactPhoneData) phoneContactSegmentProvider.newInstance(PhoneContactPhoneData.class);

                int phoneContactId = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)));

                phoneContactPhoneData.setIdAndroid(phoneContactId);
                phoneContactPhoneData.setPhoneNumber(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                phoneContactPhoneData.setPhoneType(Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))));

                phoneContactPhoneDataArrayList.add(phoneContactPhoneData);

            }
            cursor.close();
        }
        return phoneContactPhoneDataArrayList;
    }
}
