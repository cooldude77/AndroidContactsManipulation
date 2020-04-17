package com.instanect.androidContactsManipulationModule.api.query.extractors.extractor;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactNoteMapper;
import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.structs.notes.PhoneContactNoteData;

import java.util.ArrayList;

public class PhoneContactNoteDataExtractor implements PhoneContactArrayListDataExtractorInterface {


    private ContentResolver contentResolver;
    private PhoneContactNoteMapper phoneContactNoteMapper;

    public PhoneContactNoteDataExtractor(
            PhoneContactNoteMapper phoneContactNoteMapper,
            ContentResolver contentResolver) {
        this.phoneContactNoteMapper = phoneContactNoteMapper;

        this.contentResolver = contentResolver;
    }

    public ArrayList<PhoneContactNoteData> extract(int id) {


        String noteWhere = ContactsContract.Data.RAW_CONTACT_ID + " = ? AND "
                + ContactsContract.Data.MIMETYPE + " = " +
                "'" + ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE + "'";
        String[] noteWhereParams = new String[]{String.valueOf(id)};
        Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                null, noteWhere, noteWhereParams, null);

        return phoneContactNoteMapper.map(cursor);
    }
}
