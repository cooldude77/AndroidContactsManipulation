package com.instanect.androidContactsManipulationModule.api.query.cursorMappers;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactPhoneData;

import java.util.ArrayList;

public class PhoneContactPhoneMapper {
    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;

    public PhoneContactPhoneMapper(
            PhoneContactSegmentProvider phoneContactSegmentProvider,
            PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
    }

    public ArrayList<PhoneContactPhoneData> map(Cursor cursor) {
        ArrayList<PhoneContactPhoneData> phoneContactPhoneDataArrayList
                = phoneContactArrayListSegmentProvider.newInstance(PhoneContactPhoneData.class);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                PhoneContactPhoneData phoneContactPhoneData =
                        (PhoneContactPhoneData) phoneContactSegmentProvider.newInstance(PhoneContactPhoneData.class);

                int phoneContactId = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)));

                phoneContactPhoneData.setIdRaw(phoneContactId);
                //   phoneContactPhoneData.setIdAndroid(phoneContactId);
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
