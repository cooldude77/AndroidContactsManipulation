package com.instanect.androidContactsManipulationModule.api.query.extractors;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactWebData;

import java.util.ArrayList;

public class PhoneContactWebDataExtractor implements PhoneContactArrayListDataExtractorInterface {


    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;
    private ContentResolver contentResolver;

    public PhoneContactWebDataExtractor(PhoneContactSegmentProvider phoneContactSegmentProvider,
                                        PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider,
                                        ContentResolver contentResolver) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public ArrayList<PhoneContactWebData> extract(int id) {

        ArrayList<PhoneContactWebData> phoneContactWebDataArrayList
                = phoneContactArrayListSegmentProvider.newInstance(PhoneContactWebData.class);

        // get the phone number
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{String.valueOf(id)}, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                PhoneContactWebData phoneContactWebData =
                        (PhoneContactWebData) phoneContactSegmentProvider
                                .newInstance(PhoneContactWebData.class);

                int webContactId = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website._ID)));


                String url = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL));
                int urlType = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE)));

                phoneContactWebData.setIdAndroid(webContactId);
                phoneContactWebData.setUrl(url);
                phoneContactWebData.setUrlType(urlType);
            }
            cursor.close();
        }
        return phoneContactWebDataArrayList;
    }
}
