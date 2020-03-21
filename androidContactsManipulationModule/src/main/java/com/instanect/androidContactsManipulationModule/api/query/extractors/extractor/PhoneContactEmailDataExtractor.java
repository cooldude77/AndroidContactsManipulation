package com.instanect.androidContactsManipulationModule.api.query.extractors.extractor;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactEmailMapper;
import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactEmailData;

import java.util.ArrayList;

public class PhoneContactEmailDataExtractor implements PhoneContactArrayListDataExtractorInterface {


    private PhoneContactEmailMapper phoneContactEmailMapper;
    private ContentResolver contentResolver;

    public PhoneContactEmailDataExtractor(
            PhoneContactEmailMapper phoneContactEmailMapper,
            ContentResolver contentResolver) {
        this.phoneContactEmailMapper = phoneContactEmailMapper;
        this.contentResolver = contentResolver;
    }

    public ArrayList<PhoneContactEmailData> extract(int rawContactId) {


        // get email and type
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.RAW_CONTACT_ID + " = ?",
                new String[]{String.valueOf(rawContactId)}, null);

        return phoneContactEmailMapper.map(cursor);
    }
}
