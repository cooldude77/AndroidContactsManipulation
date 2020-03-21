package com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces.PhoneContactMapperArrayListInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactWebData;

import java.util.ArrayList;

public class PhoneContactWebMapper implements PhoneContactMapperArrayListInterface {
    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;

    public PhoneContactWebMapper(
            PhoneContactSegmentProvider phoneContactSegmentProvider,
            PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
    }

    public ArrayList<PhoneContactWebData> map(Cursor cursor) {

        ArrayList<PhoneContactWebData> phoneContactWebDataArrayList
                = phoneContactArrayListSegmentProvider.newInstance(PhoneContactWebData.class);


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                PhoneContactWebData phoneContactWebData =
                        (PhoneContactWebData) phoneContactSegmentProvider
                                .newInstance(PhoneContactWebData.class);

                int webContactId = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website._ID)));


                String url = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL));
                int urlType = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE)));

                phoneContactWebData.setId(webContactId);
                phoneContactWebData.setIdAndroid(webContactId);
                phoneContactWebData.setUrl(url);
                phoneContactWebData.setUrlType(urlType);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return phoneContactWebDataArrayList;


    }

}
