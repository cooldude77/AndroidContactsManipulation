package com.instanect.androidContactsManipulationModule.api.query.extractors.extractor;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactAddressMapper;
import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.address.PhoneContactAddressData;

import java.util.ArrayList;

public class PhoneContactAddressDataExtractor implements PhoneContactArrayListDataExtractorInterface {


    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;
    private ContentResolver contentResolver;
    private PhoneContactAddressMapper phoneContactAddressMapper;

    public PhoneContactAddressDataExtractor(
            PhoneContactAddressMapper phoneContactAddressMapper,
            ContentResolver contentResolver) {
        this.phoneContactAddressMapper = phoneContactAddressMapper;
        this.contentResolver = contentResolver;
    }

    public ArrayList<PhoneContactAddressData> extract(int id) {

        //Get Postal Address....

        String whereCond = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereParams = new String[]{String.valueOf(id), ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};

        Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, whereCond, whereParams, null);

        return phoneContactAddressMapper.map(cursor);
    }
}
