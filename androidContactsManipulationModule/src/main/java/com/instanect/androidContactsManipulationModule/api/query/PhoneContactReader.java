package com.instanect.androidContactsManipulationModule.api.query;

import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.PhoneContactExtractorMain;

public class PhoneContactReader extends AsyncTask<Integer, Void, Void> {


    private PhoneContactCursor phoneContactCursor;
    private PhoneContactAndroidObject phoneContactAndroidObject;
    private PhoneContactExtractorMain phoneContactExtractorMain;
    private PhoneContactSingleReaderReadingProgressInterface phoneContactSingleReaderReadingProgressInterface;

    public PhoneContactReader(
            PhoneContactCursor phoneContactCursor,
            PhoneContactExtractorMain phoneContactExtractorMain,
            PhoneContactSingleReaderReadingProgressInterface
                    phoneContactSingleReaderReadingProgressInterface) {
        this.phoneContactCursor = phoneContactCursor;
        this.phoneContactExtractorMain = phoneContactExtractorMain;
        this.phoneContactSingleReaderReadingProgressInterface = phoneContactSingleReaderReadingProgressInterface;
    }

    @Override
    protected Void doInBackground(Integer... integers) {

        int id = integers[0];
        Cursor cursor = phoneContactCursor.getCursor(ContactsContract.Contacts._ID + "=?",
                new String[]{String.valueOf(id)});

        // do nor delete
        //contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
        //              null, ContactsContract.Contacts.DISPLAY_NAME+" like ?",
        //            new String[]{"%deep krishna%"}, null);


        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            phoneContactAndroidObject
                    = phoneContactExtractorMain.getPhoneContactObject(cursor);

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        phoneContactSingleReaderReadingProgressInterface.isComplete(phoneContactAndroidObject);
    }
}