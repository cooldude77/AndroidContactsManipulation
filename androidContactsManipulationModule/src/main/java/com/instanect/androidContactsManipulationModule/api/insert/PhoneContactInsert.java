package com.instanect.androidContactsManipulationModule.api.insert;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.instanect.androidContactsManipulationModule.structs.PhoneContactCompleteObject;
import com.instanect.androidContactsManipulationModule.structs.accountType.PhoneContactAccountType;
import com.instanect.androidContactsManipulationModule.structs.address.PhoneContactAddressData;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactEmailData;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactPhoneData;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactWebData;
import com.instanect.androidContactsManipulationModule.structs.notes.PhoneContactNoteData;
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactUserData;
import com.instanect.androidContactsManipulationModule.structs.work.PhoneContactWorkData;

import java.util.ArrayList;

public class PhoneContactInsert {

    private Context context;

    public PhoneContactInsert(Context context) {

        this.context = context;
    }

    private ArrayList<ContentProviderOperation> ops;
    private int rawContactInsertIndex;

    public ContentProviderResult[] addContactSingle(PhoneContactCompleteObject phoneContactCompleteObject) {

        ops = new ArrayList<ContentProviderOperation>();
        rawContactInsertIndex = ops.size();

        // order is very important please don't change
        setAccountType(phoneContactCompleteObject.getPhoneContactAccountType());

        addNameData(phoneContactCompleteObject.getPhoneContactNameData());
        addWorkData(phoneContactCompleteObject.getPhoneContactWorkData());

        addEmailData(phoneContactCompleteObject.getPhoneContactEmailDataList());
        addPhoneData(phoneContactCompleteObject.getPhoneContactPhoneDataList());
        addWebData(phoneContactCompleteObject.getPhoneContactWebDataList());

        addNoteData(phoneContactCompleteObject.getPhoneContactNoteDataList());

        addAddressData(phoneContactCompleteObject.getPhoneContactAddressDataList());

        // return null;
        return apply();

    }

    public ContentProviderResult[] apply() {
        try {
            return context
                    .getContentResolver().
                            applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            // do s.th.
        } catch (OperationApplicationException e) {
            // do s.th.
        }
        return new ContentProviderResult[0];
    }

    private void setAccountType(@NonNull PhoneContactAccountType phoneContactAccountType) {



        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,
                        phoneContactAccountType.getAccountType())
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME,
                        phoneContactAccountType.getAccountName())
                .build());

    }

    private void addNameData(PhoneContactUserData phoneContactUserData) {
        ContentProviderOperation.Builder builder = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex);
        ops.add(getMappedNameEntity(builder, phoneContactUserData).build());
    }

    private void addWorkData(PhoneContactWorkData phoneContactWorkData) {
        ContentProviderOperation.Builder builder = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex);
        ops.add(getMappedWorkEntity(builder, phoneContactWorkData).build());
    }

    private ContentProviderOperation.Builder getMappedWorkEntity(ContentProviderOperation.Builder builder, PhoneContactWorkData phoneContactWorkData) {

        return builder.withValue(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY,
                        phoneContactWorkData.getCompany())
                .withValue(ContactsContract.CommonDataKinds.Organization.DEPARTMENT,
                        phoneContactWorkData.getDepartment())
                .withValue(ContactsContract.CommonDataKinds.Organization.TITLE,
                        phoneContactWorkData.getJobTitle());
    }

    private ContentProviderOperation.Builder getMappedNameEntity(
            ContentProviderOperation.Builder builder,
            PhoneContactUserData phoneContactUserData) {
        return builder.withValue(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        phoneContactUserData.getDisplayName())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                        phoneContactUserData.getFirstName())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.SUFFIX,
                        phoneContactUserData.getSuffix())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.PREFIX,
                        phoneContactUserData.getPrefix())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
                        phoneContactUserData.getLastName())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME,
                        phoneContactUserData.getMiddleName());
    }

    private void addPhoneData(ArrayList<PhoneContactPhoneData> phoneContactPhoneDataList) {

        for (PhoneContactPhoneData phoneContactPhoneData : phoneContactPhoneDataList) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                            phoneContactPhoneData.getPhoneNumber())
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            phoneContactPhoneData.getPhoneType())
                    .build());
        }
    }

    private void addEmailData(ArrayList<PhoneContactEmailData> phoneContactEmailDataList) {
        for (PhoneContactEmailData phoneContactEmailData : phoneContactEmailDataList) {

            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA,
                            phoneContactEmailData.getEmail())
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                            phoneContactEmailData.getEmailType())
                    .build());
        }

    }


    private void addAddressData(ArrayList<PhoneContactAddressData> phoneContactAddressDataList) {
        for (PhoneContactAddressData phoneContactAddressData : phoneContactAddressDataList) {

            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.STREET,
                            phoneContactAddressData.getStreet())
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.POBOX,
                            phoneContactAddressData.getPoBox())
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.CITY,
                            phoneContactAddressData.getCity())
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.REGION,
                            phoneContactAddressData.getState())
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY,
                            phoneContactAddressData.getCountry())
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE,
                            phoneContactAddressData.getPostalCode())
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
                            phoneContactAddressData.getAddressType())
                    .build());
        }

    }

    private void addNoteData(ArrayList<PhoneContactNoteData> phoneContactNoteDataList) {

        for (PhoneContactNoteData phoneContactNoteData : phoneContactNoteDataList)
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Note.NOTE,
                            phoneContactNoteData.getNote())
                    .build());
    }

    private void addWebData(ArrayList<PhoneContactWebData> phoneContactWebDataList) {
        for (PhoneContactWebData phoneContactWebData : phoneContactWebDataList) {

            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Website.DATA,
                            phoneContactWebData.getUrl())
                    .withValue(ContactsContract.CommonDataKinds.Website.TYPE,
                            phoneContactWebData.getUrlType())
                    .build());
        }

    }


    public ArrayList<ContentProviderResult[]> addContactMultiple(
            ArrayList<PhoneContactCompleteObject> phoneContactCompleteObjects) {

        ArrayList<ContentProviderResult[]> arrayList = new ArrayList<>();
        for (PhoneContactCompleteObject phoneContactCompleteObject : phoneContactCompleteObjects)
            arrayList.add(addContactSingle(phoneContactCompleteObject));

        return arrayList;
    }
}
