package com.instanect.androidContactsManipulationModule.api;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.ContactsContract;

import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactAccountType;
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactCompleteObject;
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactNameData;

import java.util.ArrayList;

public class ContactsApi {

    private Context context;

    public ContactsApi(Context context) {

        this.context = context;

    }

    ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
    int rawContactInsertIndex = ops.size();

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

            cur.moveToFirst();
            String str = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        int i=0;
        i++;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addContact(PhoneContactCompleteObject phoneContactCompleteObject) {

        setAccountType(phoneContactCompleteObject.getPhoneContactAccountType());
        addNameData(phoneContactCompleteObject.getPhoneContactNameData());
        apply();
    }

    private void apply() {
        try {
            context
                    .getContentResolver().
                    applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            // do s.th.
        } catch (OperationApplicationException e) {
            // do s.th.
        }
    }

    public void setAccountType(PhoneContactAccountType phoneContactAccountType) {
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

}
