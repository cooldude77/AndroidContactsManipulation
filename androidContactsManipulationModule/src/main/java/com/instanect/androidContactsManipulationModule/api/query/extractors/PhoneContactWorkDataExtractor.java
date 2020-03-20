package com.instanect.androidContactsManipulationModule.api.query.extractors;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.work.PhoneContactWorkData;

public class PhoneContactWorkDataExtractor implements PhoneContactDataExtractorInterface {


    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private ContentResolver contentResolver;

    public PhoneContactWorkDataExtractor(PhoneContactSegmentProvider phoneContactSegmentProvider,
                                         ContentResolver contentResolver) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public PhoneContactWorkData extract(int rawId) {
        PhoneContactWorkData phoneContactWorkData = (PhoneContactWorkData) phoneContactSegmentProvider
                .newInstance(PhoneContactWorkData.class);


        // Get Organizations.........
        String whereName = ContactsContract.Data.MIMETYPE + " = ? AND "
                + ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID + " = ?";
        String[] whereNameParams = new String[]{
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE, String.valueOf(rawId)};


        Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                null, whereName, whereNameParams, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                int idAndroid = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization._ID));
                String orgName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
                String title = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                String department = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT));


                phoneContactWorkData.setDepartment(department);
                phoneContactWorkData.setCompany(orgName);
                phoneContactWorkData.setJobTitle(title);


            }
            cursor.close();
        }
        return phoneContactWorkData;
    }
}
