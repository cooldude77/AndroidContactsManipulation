package com.instanect.androidContactsManipulationModule.api.query.extractors;


import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.PhoneContactObjectProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.factory.PhoneContactExtractorIntoArrayListProviderFactory;
import com.instanect.androidContactsManipulationModule.api.query.extractors.factory.PhoneContactExtractorProviderFactory;
import com.instanect.androidContactsManipulationModule.structs.PhoneContactCompleteObject;
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactUserData;
import com.instanect.androidContactsManipulationModule.structs.work.PhoneContactWorkData;

public class PhoneContactExtractorMain {

    private PhoneContactObjectProvider phoneContactObjectProvider;
    private PhoneContactExtractorProviderFactory phoneContactExtractorProviderFactory;
    private PhoneContactExtractorIntoArrayListProviderFactory phoneContactExtractorIntoArrayListProviderFactory;

    public PhoneContactExtractorMain(PhoneContactObjectProvider phoneContactObjectProvider, PhoneContactExtractorProviderFactory phoneContactExtractorProviderFactory, PhoneContactExtractorIntoArrayListProviderFactory phoneContactExtractorIntoArrayListProviderFactory) {
        this.phoneContactObjectProvider = phoneContactObjectProvider;
        this.phoneContactExtractorProviderFactory = phoneContactExtractorProviderFactory;
        this.phoneContactExtractorIntoArrayListProviderFactory = phoneContactExtractorIntoArrayListProviderFactory;
    }


    public PhoneContactCompleteObject getPhoneContactObject(Cursor cursor) {

        PhoneContactCompleteObject phoneContactCompleteObject = phoneContactObjectProvider.newInstance();

        int rawId = Integer.parseInt(cursor
                .getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));

        // name and others
        PhoneContactUserDataExtractor phoneContactUserDataExtractor
                = (PhoneContactUserDataExtractor) phoneContactExtractorProviderFactory.getExtractor(PhoneContactUserDataExtractor.class);

        PhoneContactUserData phoneContactUserData = phoneContactUserDataExtractor.extract(rawId);

        phoneContactCompleteObject.setPhoneContactNameData(phoneContactUserData);

        // work
        PhoneContactWorkDataExtractor phoneContactWorkDataExtractor
                = (PhoneContactWorkDataExtractor) phoneContactExtractorProviderFactory.getExtractor(PhoneContactWorkDataExtractor.class);

        PhoneContactWorkData phoneContactWorkData = phoneContactWorkDataExtractor.extract(rawId);

        phoneContactCompleteObject.setPhoneContactWorkData(phoneContactWorkData);

        // email
        PhoneContactEmailDataExtractor phoneContactEmailDataExtractor
                = (PhoneContactEmailDataExtractor)
                phoneContactExtractorIntoArrayListProviderFactory
                        .getExtractor(PhoneContactEmailDataExtractor.class);

        phoneContactCompleteObject.setPhoneContactEmailDataList(
                phoneContactEmailDataExtractor.extract(rawId)
        );

        // phone
        PhoneContactPhoneDataExtractor phoneContactPhoneDataExtractor
                = (PhoneContactPhoneDataExtractor)
                phoneContactExtractorIntoArrayListProviderFactory
                        .getExtractor(PhoneContactPhoneDataExtractor.class);


        phoneContactCompleteObject.setPhoneContactPhoneDataList(
                phoneContactPhoneDataExtractor.extract(rawId)
        );


        // web
        PhoneContactWebDataExtractor phoneContactWebDataExtractor
                = (PhoneContactWebDataExtractor)
                phoneContactExtractorIntoArrayListProviderFactory
                        .getExtractor(PhoneContactWebDataExtractor.class);

        phoneContactCompleteObject.setPhoneContactWebDataList(
                phoneContactWebDataExtractor.extract(rawId)
        );

                    /*

                    // Get Instant Messenger.........
                    String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] imWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                    Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, imWhere, imWhereParams, null);
                    if (imCur.moveToFirst()) {
                        String imName = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                        String imType;
                        imType = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                    }
                    imCur.close();
                */

        // note
        PhoneContactNoteDataExtractor phoneContactNoteDataExtractor
                = (PhoneContactNoteDataExtractor)
                phoneContactExtractorIntoArrayListProviderFactory
                        .getExtractor(PhoneContactNoteDataExtractor.class);

        phoneContactCompleteObject.setPhoneContactNoteDataList(
                phoneContactNoteDataExtractor.extract(rawId)
        );

        // address
        PhoneContactAddressDataExtractor phoneContactAddressDataExtractor
                = (PhoneContactAddressDataExtractor)
                phoneContactExtractorIntoArrayListProviderFactory
                        .getExtractor(PhoneContactAddressDataExtractor.class);


        phoneContactCompleteObject.setPhoneContactAddressDataList(
                phoneContactAddressDataExtractor.extract(rawId)
        );


        return phoneContactCompleteObject;

    }
}
