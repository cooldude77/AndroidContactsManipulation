package com.instanect.androidContactsManipulationModule.api.query.extractors.extractor;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactNoteMapper;
import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.notes.PhoneContactNoteData;

import java.util.ArrayList;

public class PhoneContactNoteDataExtractor implements PhoneContactArrayListDataExtractorInterface {


    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;
    private ContentResolver contentResolver;
    private PhoneContactNoteMapper phoneContactNoteMapper;

    public PhoneContactNoteDataExtractor(
            PhoneContactNoteMapper phoneContactNoteMapper,
            ContentResolver contentResolver) {
        this.phoneContactNoteMapper = phoneContactNoteMapper;

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public ArrayList<PhoneContactNoteData> extract(int id) {


        String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] noteWhereParams = new String[]{String.valueOf(id),
                ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
        Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);

        return phoneContactNoteMapper.map(cursor);
    }
}
