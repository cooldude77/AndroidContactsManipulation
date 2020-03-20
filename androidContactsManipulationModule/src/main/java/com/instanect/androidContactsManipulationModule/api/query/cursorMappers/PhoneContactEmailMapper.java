package com.instanect.androidContactsManipulationModule.api.query.cursorMappers;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactEmailData;

import java.util.ArrayList;

public class PhoneContactEmailMapper {
    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;

    public PhoneContactEmailMapper(
            PhoneContactSegmentProvider phoneContactSegmentProvider,
            PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
    }

    public ArrayList<PhoneContactEmailData> map(Cursor cursor) {
        ArrayList<PhoneContactEmailData> phoneContactEmailDataArrayList
                = phoneContactArrayListSegmentProvider.newInstance(PhoneContactEmailData.class);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                PhoneContactEmailData phoneContactEmailData =
                        (PhoneContactEmailData)
                                phoneContactSegmentProvider.newInstance(PhoneContactEmailData.class);
                // This would allow you get several email addresses
                // if the email addresses were stored in an array

                int emailRawId = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email._ID)));

                String email = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                int emailType = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)));

                phoneContactEmailData.setId(emailRawId);
                phoneContactEmailData.setEmail(email);
                phoneContactEmailData.setEmailType(emailType);

                phoneContactEmailDataArrayList.add(phoneContactEmailData);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return phoneContactEmailDataArrayList;
    }
}
