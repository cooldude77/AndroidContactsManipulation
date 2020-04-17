package com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces.PhoneContactMapperInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactUserData;

public class PhoneContactUserDataMapper implements PhoneContactMapperInterface {
    private PhoneContactSegmentProvider phoneContactSegmentProvider;

    public PhoneContactUserDataMapper(PhoneContactSegmentProvider phoneContactSegmentProvider) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
    }

    @Override
    public PhoneContactUserData map(Cursor cursor) {

        PhoneContactUserData phoneContactUserData = (PhoneContactUserData) phoneContactSegmentProvider.newInstance(PhoneContactUserData.class);

        int raw_contact_id = cursor.getInt(cursor.getColumnIndex("raw_contact_id"));

        phoneContactUserData.setRawContactId(raw_contact_id);

        phoneContactUserData.setRawId(
                cursor.getInt(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.StructuredName._ID))

        );
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

        return phoneContactUserData;

    }

}
