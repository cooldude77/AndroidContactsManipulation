package com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces.PhoneContactMapperArrayListInterface;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces.PhoneContactMapperInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactEmailData;
import com.instanect.androidContactsManipulationModule.structs.notes.PhoneContactNoteData;

import java.util.ArrayList;

public class PhoneContactNoteMapper implements PhoneContactMapperArrayListInterface {
    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;

    public PhoneContactNoteMapper(
            PhoneContactSegmentProvider phoneContactSegmentProvider,
            PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
    }

    public ArrayList<PhoneContactNoteData> map(Cursor cursor) {
        ArrayList<PhoneContactNoteData> phoneContactNoteDataArrayList
                = phoneContactArrayListSegmentProvider.newInstance(PhoneContactEmailData.class);

        if (cursor != null) {
            cursor.moveToFirst();
            do {

                PhoneContactNoteData phoneContactNoteData = (PhoneContactNoteData) phoneContactSegmentProvider
                        .newInstance(PhoneContactNoteData.class);


                int noteId = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note._ID)));
                String note = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));

                phoneContactNoteData.setId(noteId);
                phoneContactNoteData.setNote(note);
                phoneContactNoteDataArrayList.add(phoneContactNoteData);

            } while (cursor.moveToFirst());
            cursor.close();
        }

        return phoneContactNoteDataArrayList;
    }
}
