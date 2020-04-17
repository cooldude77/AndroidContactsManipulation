package com.instanect.androidContactsManipulationModule.api.query.extractors.extractor;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactWebMapper;
import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactWebData;

import java.util.ArrayList;

public class PhoneContactWebDataExtractor implements PhoneContactArrayListDataExtractorInterface {


    private PhoneContactWebMapper phoneContactWebMapper;
    private ContentResolver contentResolver;

    public PhoneContactWebDataExtractor(
            PhoneContactWebMapper phoneContactWebMapper,
            ContentResolver contentResolver) {
        this.phoneContactWebMapper = phoneContactWebMapper;

        this.contentResolver = contentResolver;
    }

    public ArrayList<PhoneContactWebData> extract(int id) {

        // get the phone number
        Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Website.RAW_CONTACT_ID + " = ? AND "
                        + ContactsContract.Data.MIMETYPE + " = " +
                        "'" + ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE + "'"
                ,
                new String[]{String.valueOf(id)}, null);
        return phoneContactWebMapper.map(cursor);
    }
}
