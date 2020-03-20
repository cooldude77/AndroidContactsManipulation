package com.instanect.androidContactsManipulationModule.api.query.extractors;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactEmailData;
import com.instanect.androidContactsManipulationModule.structs.notes.PhoneContactNoteData;

import java.util.ArrayList;

public class PhoneContactNoteDataExtractor implements PhoneContactArrayListDataExtractorInterface {


    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;
    private ContentResolver contentResolver;

    public PhoneContactNoteDataExtractor(PhoneContactSegmentProvider phoneContactSegmentProvider,
                                         PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider,
                                         ContentResolver contentResolver) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public ArrayList<PhoneContactNoteData> extract(int id) {

        ArrayList<PhoneContactNoteData> phoneContactNoteDataArrayList
                = phoneContactArrayListSegmentProvider.newInstance(PhoneContactEmailData.class);


        String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] noteWhereParams = new String[]{String.valueOf(id),
                ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
        Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                PhoneContactNoteData phoneContactNoteData = (PhoneContactNoteData) phoneContactSegmentProvider
                        .newInstance(PhoneContactNoteData.class);


                int noteId = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note._ID)));
                String note = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));

                phoneContactNoteData.setId(noteId);
                phoneContactNoteData.setNote(note);
                phoneContactNoteDataArrayList.add(phoneContactNoteData);

            }
            cursor.close();
        }

        return phoneContactNoteDataArrayList;
    }
}
