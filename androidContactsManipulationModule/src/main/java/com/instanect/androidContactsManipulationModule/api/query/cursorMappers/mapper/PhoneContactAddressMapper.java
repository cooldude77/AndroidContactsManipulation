package com.instanect.androidContactsManipulationModule.api.query.cursorMappers.mapper;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces.PhoneContactMapperArrayListInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;
import com.instanect.androidContactsManipulationModule.structs.address.PhoneContactAddressData;

import java.util.ArrayList;

public class PhoneContactAddressMapper implements PhoneContactMapperArrayListInterface {

    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;

    public PhoneContactAddressMapper(
            PhoneContactSegmentProvider phoneContactSegmentProvider,
            PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider) {

        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
    }

    public ArrayList<PhoneContactAddressData> map(Cursor cursor) {
        ArrayList<PhoneContactAddressData> phoneContactAddressDataArrayList
                = phoneContactArrayListSegmentProvider.newInstance(PhoneContactAddressData.class);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
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

                phoneContactAddressData.setRawId(addressContactId);
                phoneContactAddressData.setPostalCode(postalCode);
                phoneContactAddressData.setPoBox(poBox);
                phoneContactAddressData.setCountry(country);
                phoneContactAddressData.setStreet(street);
                phoneContactAddressData.setRegion(region);
                phoneContactAddressData.setCity(city);
                phoneContactAddressData.setAddressType(Integer.valueOf(type));

                phoneContactAddressDataArrayList.add(phoneContactAddressData);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return phoneContactAddressDataArrayList;
    }
}