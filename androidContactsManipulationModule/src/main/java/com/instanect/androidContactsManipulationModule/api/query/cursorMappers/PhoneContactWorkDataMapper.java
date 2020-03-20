package com.instanect.androidContactsManipulationModule.api.query.cursorMappers;


import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.work.PhoneContactWorkData;

public class PhoneContactWorkDataMapper {
    private PhoneContactSegmentProvider phoneContactSegmentProvider;

    public PhoneContactWorkDataMapper(PhoneContactSegmentProvider phoneContactSegmentProvider) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
    }

    public PhoneContactWorkData map(Cursor cursor) {

        PhoneContactWorkData phoneContactWorkData = (PhoneContactWorkData)
                phoneContactSegmentProvider.newInstance(PhoneContactWorkData.class);


        int idAndroid = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization._ID));
        String orgName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
        String title = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
        String department = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT));

        phoneContactWorkData.setId(
                cursor.getInt(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Organization._ID))

        );

        phoneContactWorkData.setDepartment(department);
        phoneContactWorkData.setCompany(orgName);
        phoneContactWorkData.setJobTitle(title);

        return phoneContactWorkData;

    }
}
