package com.instanect.androidContactsManipulationModule.api.query.extractors.extractor.main;


import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.PhoneContactObjectProvider;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactAddressMapper;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactEmailMapper;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactNoteMapper;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactPhoneMapper;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactUserDataMapper;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactWebMapper;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper.PhoneContactWorkDataMapper;
import com.instanect.androidContactsManipulationModule.api.query.extractors.extractor.PhoneContactAddressDataExtractor;
import com.instanect.androidContactsManipulationModule.api.query.extractors.extractor.PhoneContactEmailDataExtractor;
import com.instanect.androidContactsManipulationModule.api.query.extractors.extractor.PhoneContactNoteDataExtractor;
import com.instanect.androidContactsManipulationModule.api.query.extractors.extractor.PhoneContactPhoneDataExtractor;
import com.instanect.androidContactsManipulationModule.api.query.extractors.extractor.PhoneContactUserDataExtractor;
import com.instanect.androidContactsManipulationModule.api.query.extractors.extractor.PhoneContactWebDataExtractor;
import com.instanect.androidContactsManipulationModule.api.query.extractors.extractor.PhoneContactWorkDataExtractor;
import com.instanect.androidContactsManipulationModule.api.query.extractors.factory.PhoneContactExtractorIntoArrayListProviderFactory;
import com.instanect.androidContactsManipulationModule.api.query.extractors.factory.PhoneContactExtractorProviderFactory;
import com.instanect.androidContactsManipulationModule.structs.PhoneContactCompleteObject;
import com.instanect.androidContactsManipulationModule.structs.address.PhoneContactAddressData;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactEmailData;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactPhoneData;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactWebData;
import com.instanect.androidContactsManipulationModule.structs.notes.PhoneContactNoteData;
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactUserData;
import com.instanect.androidContactsManipulationModule.structs.work.PhoneContactWorkData;

import java.util.ArrayList;

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

        phoneContactCompleteObject.setPhoneContactNameData(getUserDataByContactRawId(rawId));
        phoneContactCompleteObject.setPhoneContactWorkData(getWorkDataByContactRawId(rawId));
        phoneContactCompleteObject.setPhoneContactEmailDataList(getEmailDataByContactRawId(rawId));
        phoneContactCompleteObject.setPhoneContactPhoneDataList(getPhoneDataByContactRawId(rawId));
        phoneContactCompleteObject.setPhoneContactWebDataList(getWebDataByContactRawId(rawId));
        phoneContactCompleteObject.setPhoneContactNoteDataList(getNoteDataByContactRawId(rawId));
        phoneContactCompleteObject.setPhoneContactAddressDataList(getAddressDataByContactRawId(rawId));

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


        return phoneContactCompleteObject;

    }

    private ArrayList<PhoneContactWebData> getWebDataByContactRawId(int rawId) {
        // web
        PhoneContactWebDataExtractor phoneContactWebDataExtractor
                = (PhoneContactWebDataExtractor)
                phoneContactExtractorIntoArrayListProviderFactory
                        .getExtractor(PhoneContactWebDataExtractor.class,
                                PhoneContactWebMapper.class);


        return phoneContactWebDataExtractor.extract(rawId);
    }

    private ArrayList<PhoneContactAddressData> getAddressDataByContactRawId(int rawId) {
        PhoneContactAddressDataExtractor phoneContactAddressDataExtractor
                = (PhoneContactAddressDataExtractor)
                phoneContactExtractorIntoArrayListProviderFactory
                        .getExtractor(PhoneContactAddressDataExtractor.class,
                                PhoneContactAddressMapper.class);

        return phoneContactAddressDataExtractor.extract(rawId);
    }

    private ArrayList<PhoneContactNoteData> getNoteDataByContactRawId(int rawId) {

        // note
        PhoneContactNoteDataExtractor phoneContactNoteDataExtractor
                = (PhoneContactNoteDataExtractor)
                phoneContactExtractorIntoArrayListProviderFactory
                        .getExtractor(PhoneContactNoteDataExtractor.class,
                                PhoneContactNoteMapper.class);

        return phoneContactNoteDataExtractor.extract(rawId);
    }

    private ArrayList<PhoneContactPhoneData> getPhoneDataByContactRawId(int rawId) {

        // phone
        PhoneContactPhoneDataExtractor phoneContactPhoneDataExtractor
                = (PhoneContactPhoneDataExtractor)
                phoneContactExtractorIntoArrayListProviderFactory
                        .getExtractor(PhoneContactPhoneDataExtractor.class,
                                PhoneContactPhoneMapper.class);

        return phoneContactPhoneDataExtractor.extract(rawId);
    }

    private ArrayList<PhoneContactEmailData> getEmailDataByContactRawId(int rawId) {

        // email
        PhoneContactEmailDataExtractor phoneContactEmailDataExtractor
                = (PhoneContactEmailDataExtractor)
                phoneContactExtractorIntoArrayListProviderFactory
                        .getExtractor(PhoneContactEmailDataExtractor.class,
                                PhoneContactEmailMapper.class);

        return phoneContactEmailDataExtractor.extract(rawId);

    }

    private PhoneContactWorkData getWorkDataByContactRawId(int rawId) {
        // work
        PhoneContactWorkDataExtractor phoneContactWorkDataExtractor
                = (PhoneContactWorkDataExtractor) phoneContactExtractorProviderFactory
                .getExtractor(PhoneContactWorkDataExtractor.class, PhoneContactWorkDataMapper.class);

        return phoneContactWorkDataExtractor.extract(rawId);

    }

    public PhoneContactUserData getUserDataByContactRawId(int rawId) {


        // name and others
        PhoneContactUserDataExtractor phoneContactUserDataExtractor
                = (PhoneContactUserDataExtractor) phoneContactExtractorProviderFactory
                .getExtractor(PhoneContactUserDataExtractor.class, PhoneContactUserDataMapper.class);

        return phoneContactUserDataExtractor.extract(rawId);

    }
}
