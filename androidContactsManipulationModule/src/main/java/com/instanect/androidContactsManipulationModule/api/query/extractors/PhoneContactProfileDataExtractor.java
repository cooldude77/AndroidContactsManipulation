package com.instanect.androidContactsManipulationModule.api.query.extractors;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.work.PhoneContactWorkData;

public class PhoneContactProfileDataExtractor implements PhoneContactDataExtractorInterface {


    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private ContentResolver contentResolver;

    public PhoneContactProfileDataExtractor(PhoneContactSegmentProvider phoneContactSegmentProvider,
                                            ContentResolver contentResolver) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public PhoneContactWorkData extract(int id) {
        PhoneContactWorkData phoneContactProfileData = (PhoneContactWorkData) phoneContactSegmentProvider
                .newInstance(PhoneContactWorkData.class);


        // Get Organizations.........

        String whereCond = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereParams = new String[]{String.valueOf(id),
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
        Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                null, whereCond, whereParams, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                int idAndroid =  cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization._ID));
                String orgName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
                String title = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                String department = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT));


                phoneContactProfileData.setDepartment(department);
                phoneContactProfileData.setCompany(orgName);
                phoneContactProfileData.setJobTitle(title);


            }
            cursor.close();
        }
        return phoneContactProfileData;
    }
}
