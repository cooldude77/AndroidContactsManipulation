package com.instanect.androidContactsManipulationModule.api.query.extractors;


import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactDataExtractorInterface;

public class PhoneContactUserDataExtractor implements PhoneContactDataExtractorInterface {


    private PhoneContactSegmentProvider phoneContactSegmentProvider;

    public PhoneContactUserDataExtractor(PhoneContactSegmentProvider phoneContactSegmentProvider,
                                         ContentResolver contentResolver) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
    }

    public PhoneContactUserData extract(Cursor cursor) {

        PhoneContactUserData phoneContactUserData = (PhoneContactUserData) phoneContactSegmentProvider.newInstance(
                PhoneContactUserData.class
        );
        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        String uriString = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
        boolean isStarred =

                cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.STARRED)) == 1;

        phoneContactUserData.setName(name);
        phoneContactUserData.setIdAndroid(Integer.parseInt(id));

        if (uriString != null) {
            phoneContactUserData.setPhotoUri(Uri.parse(uriString));
        }
        phoneContactUserData.setStarred(isStarred);


        return phoneContactUserData;
    }

    @Override
    public PhoneContactSegmentInterface extract(int id) {
        return null;
    }
}
