package com.instanect.androidContactsManipulationModule.api.query.extractors.extractor;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactWorkDataMapper;
import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.structs.work.PhoneContactWorkData;

public class PhoneContactWorkDataExtractor implements PhoneContactDataExtractorInterface {


    private PhoneContactWorkDataMapper phoneContactWorkDataMapper;
    private ContentResolver contentResolver;

    public PhoneContactWorkDataExtractor(PhoneContactWorkDataMapper phoneContactWorkDataMapper,
                                         ContentResolver contentResolver) {
        this.phoneContactWorkDataMapper = phoneContactWorkDataMapper;

        this.contentResolver = contentResolver;
    }

    public PhoneContactWorkData extract(int rawId) {


        // Get Organizations.........
        String whereName = ContactsContract.Data.MIMETYPE + " = ? AND "
                + ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID + " = ?";
        String[] whereNameParams = new String[]{
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE, String.valueOf(rawId)};


        Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                null, whereName, whereNameParams, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                return phoneContactWorkDataMapper.map(cursor);

            }
            cursor.close();
        }
        return null;
    }
}
