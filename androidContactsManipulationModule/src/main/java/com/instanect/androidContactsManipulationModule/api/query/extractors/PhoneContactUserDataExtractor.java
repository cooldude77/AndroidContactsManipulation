package com.instanect.androidContactsManipulationModule.api.query.extractors;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactUserData;

public class PhoneContactUserDataExtractor implements PhoneContactDataExtractorInterface {


    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private ContentResolver contentResolver;

    public PhoneContactUserDataExtractor(PhoneContactSegmentProvider phoneContactSegmentProvider,
                                         ContentResolver contentResolver) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public PhoneContactUserData extract(int rawId) {

        PhoneContactUserData phoneContactUserData = (PhoneContactUserData) phoneContactSegmentProvider
                .newInstance(
                        PhoneContactUserData.class
                );


    /*
     Do Not delete
    This also can be used when contact_id is used

        int id = cursor.getInt(cursor.getColumnIndex("contact_id"));


        Cursor cursor1 = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                ContactsContract.Contacts._ID + "= ?",
                new String[]{String.valueOf(id)},
                null
        );

        String whereName = ContactsContract.Data.MIMETYPE + " = ? AND "
                + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?";
        String[] whereNameParams = new String[] {
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, "683" };

    */
        String whereName = ContactsContract.Data.MIMETYPE + " = ? AND "
                + ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID + " = ?";
        String[] whereNameParams = new String[]{
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, String.valueOf(rawId)};

        Cursor cursor = contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                whereName,
                whereNameParams,
                null
        );
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            phoneContactUserData.setFirstName(
                    cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME))
            );
            phoneContactUserData.setLastName(
                    cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)));
            phoneContactUserData.setMiddleName(
                    cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME))
            );

            phoneContactUserData.setSuffix(
                    cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.StructuredName.SUFFIX))
            );

            phoneContactUserData.setPrefix(
                    cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.StructuredName.PREFIX))
            );


            //  String uriString = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
            //  boolean isStarred =

            //        cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.STARRED)) == 1;

//        phoneContactUserData.setName(name);
            //      phoneContactUserData.setIdAndroid(Integer.parseInt(id));

            // if (uriString != null) {
            //        phoneContactUserData.setPhotoUri(Uri.parse(uriString));
            //}
            // phoneContactUserData.setStarred(isStarred);


            cursor.close();
            return phoneContactUserData;
        }
        return null;
    }
}
