package com.instanect.androidContactsManipulationModule.api;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.instanect.androidContactsManipulationModule.structs.PhoneContactCompleteObject;
import com.instanect.androidContactsManipulationModule.structs.accountType.PhoneContactAccountType;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactEmailData;
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactPhoneData;
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactNameData;
import com.instanect.androidContactsManipulationModule.structs.work.PhoneContactWorkData;

import java.util.ArrayList;

public class ContactsApi {

    private final Context context;

    public ContactsApi(Context context) {

        this.context = context;

    }

    private ArrayList<ContentProviderOperation> ops;
    private int rawContactInsertIndex;

    public void getContacts() {

        String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cur = null;

        try {
            String startsWith = "Kunja";
            cur = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projection,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                            + " like \"" + startsWith + "%\"", null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

            if (cur != null) {
                cur.moveToFirst();
                String str = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                cur.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public ContentProviderResult[] addContact(@NonNull PhoneContactCompleteObject phoneContactCompleteObject) {


        ops = new ArrayList<ContentProviderOperation>();
        rawContactInsertIndex = ops.size();

        // order is very important please don't change
        setAccountType(phoneContactCompleteObject.getPhoneContactAccountType());

        addNameData(phoneContactCompleteObject.getPhoneContactNameData());
        addWorkData(phoneContactCompleteObject.getPhoneContactWorkData());

        addEmailData(phoneContactCompleteObject.getPhoneContactEmailDataList());
        addPhoneData(phoneContactCompleteObject.getPhoneContactPhoneDataList());

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

    private void setAccountType(PhoneContactAccountType phoneContactAccountType) {
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,
                        phoneContactAccountType.getAccountType())
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME,
                        phoneContactAccountType.getAccountName())
                .build());

    }

    private void addNameData(PhoneContactNameData phoneContactNameData) {
        ContentProviderOperation.Builder builder = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex);
        ops.add(getMappedNameEntity(builder, phoneContactNameData).build());
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
                .withValue(ContactsContract.CommonDataKinds.Organization.TITLE,
                        phoneContactWorkData.getJobTitle());
    }

    private ContentProviderOperation.Builder getMappedNameEntity(
            ContentProviderOperation.Builder builder,
            PhoneContactNameData phoneContactNameData) {
        return builder.withValue(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        phoneContactNameData.getDisplayName())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                        phoneContactNameData.getFirstName())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.SUFFIX,
                        phoneContactNameData.getSuffix())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.PREFIX,
                        phoneContactNameData.getPrefix())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
                        phoneContactNameData.getLastName())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME,
                        phoneContactNameData.getMiddleName());
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
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
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
                            ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
        }

    }

    public ArrayList<ContentProviderResult[]> addContact(ArrayList<PhoneContactCompleteObject> phoneContactCompleteObjects) {

        ArrayList<ContentProviderResult[]> arrayList = new ArrayList<>();
        for (PhoneContactCompleteObject phoneContactCompleteObject : phoneContactCompleteObjects)
            arrayList.add(addContact(phoneContactCompleteObject));

        return arrayList;
    }

    public String query(int rawId) {
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.RawContacts.CONTENT_URI,
                null, null,
//                ContactsContract.Contacts._ID + "=?",new String[]{"628"},
                null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                String str = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String raw_id = cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));

                Log.d("TAG", str + ":" + id + " raw:" + raw_id);
            } while (cursor.moveToNext());
            cursor.close();
            return null;

        }


        return null;
    }

    public void updatePhoneNameData(int rawId, PhoneContactNameData phoneContactNameData) {


    }

}
