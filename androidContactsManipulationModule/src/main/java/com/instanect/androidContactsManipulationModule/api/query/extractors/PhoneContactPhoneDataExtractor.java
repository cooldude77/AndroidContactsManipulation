package com.instanect.androidContactsManipulationModule.api.query.extractors;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.PhoneContactPhoneMapper;
import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactPhoneData;

import java.util.ArrayList;

public class PhoneContactPhoneDataExtractor implements PhoneContactArrayListDataExtractorInterface {

    private PhoneContactPhoneMapper phoneContactPhoneMapper;
    private ContentResolver contentResolver;

    public PhoneContactPhoneDataExtractor(
            PhoneContactPhoneMapper phoneContactPhoneMapper,
            ContentResolver contentResolver) {
        this.phoneContactPhoneMapper = phoneContactPhoneMapper;
        this.contentResolver = contentResolver;
    }

    public ArrayList<PhoneContactPhoneData> extract(int rawContactId) {


        // get the phone number
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + " = ?",
                new String[]{String.valueOf(rawContactId)}, null);


        return phoneContactPhoneMapper.map(cursor);
    }
}
