package com.instanect.androidContactsManipulationModule.api.query.extractors;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.address.PhoneContactAddressData;

import java.util.ArrayList;

public class PhoneContactAddressDataExtractor implements PhoneContactArrayListDataExtractorInterface {


    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;
    private ContentResolver contentResolver;

    public PhoneContactAddressDataExtractor(
            PhoneContactSegmentProvider phoneContactSegmentProvider,
            PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider,
            ContentResolver contentResolver) {
        this.phoneContactSegmentProvider = phoneContactSegmentProvider;

        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public ArrayList<PhoneContactAddressData> extract(int id) {

        ArrayList<PhoneContactAddressData> phoneContactAddressDataArrayList = phoneContactArrayListSegmentProvider
                .newInstance(PhoneContactAddressData.class);

        //Get Postal Address....

        String whereCond = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereParams = new String[]{String.valueOf(id), ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};

        Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, whereCond, whereParams, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                PhoneContactAddressData phoneContactAddressData = (PhoneContactAddressData)
                        phoneContactSegmentProvider.newInstance(PhoneContactAddressData.class);
                int addressContactId = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal._ID)));


                String poBox = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                String street = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                String city = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                String region = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                String postalCode = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                String country = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                String type = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                phoneContactAddressData.setIdAndroid(addressContactId);
                phoneContactAddressData.setPostalCode(postalCode);
                phoneContactAddressData.setPoBox(poBox);
                phoneContactAddressData.setCountry(country);
                phoneContactAddressData.setStreet(street);
                phoneContactAddressData.setRegion(region);
                phoneContactAddressData.setCity(city);
                phoneContactAddressData.setType(Integer.valueOf(type));

                phoneContactAddressDataArrayList.add(phoneContactAddressData);
            }
            cursor.close();
        }

        return phoneContactAddressDataArrayList;
    }
}
