package com.instanect.androidContactsManipulationModule.api.query.extractors;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.PhoneContactUserDataMapper;
import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactUserData;

public class PhoneContactUserDataExtractor implements PhoneContactDataExtractorInterface {


    private PhoneContactUserDataMapper phoneContactUserDataMapper;
    private ContentResolver contentResolver;

    public PhoneContactUserDataExtractor(PhoneContactUserDataMapper phoneContactUserDataMapper,
                                         ContentResolver contentResolver) {

        this.phoneContactUserDataMapper = phoneContactUserDataMapper;
        this.contentResolver = contentResolver;
    }

    public PhoneContactUserData extract(int rawId) {


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

            return phoneContactUserDataMapper.map(cursor);

        }
        return null;
    }
}
