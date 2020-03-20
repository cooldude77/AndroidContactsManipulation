package com.instanect.androidContactsManipulationModule.api;

import android.content.ContentProviderResult;
import android.content.Context;

import androidx.annotation.NonNull;

import com.instanect.androidContactsManipulationModule.api.insert.PhoneContactInsert;
import com.instanect.androidContactsManipulationModule.api.query.PhoneContactReader;
import com.instanect.androidContactsManipulationModule.structs.PhoneContactCompleteObject;
import com.instanect.androidContactsManipulationModule.structs.accountType.PhoneContactAccountType;
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactUserData;

import java.util.ArrayList;

public class ContactsApi {

    private final Context context;
    private PhoneContactReader phoneContactReader;
    private PhoneContactInsert phoneContactInsert;

    public ContactsApi(Context context,
                       PhoneContactReader phoneContactReader,
                       PhoneContactInsert phoneContactInsert) {

        this.context = context;

        this.phoneContactReader = phoneContactReader;
        this.phoneContactInsert = phoneContactInsert;
    }

    /**
     * Add contact
     *
     * @param phoneContactCompleteObject PhoneContactCompleteObject
     * @return results of insert with id
     */
    public ContentProviderResult[] addContact(@NonNull PhoneContactCompleteObject phoneContactCompleteObject) {
        return phoneContactInsert.addContactSingle(phoneContactCompleteObject);
    }

    /**
     * Get Complete Object
     *
     * @param rawId raw id of row of contact for account
     * @return PhoneContactCompleteObject
     */
    public PhoneContactCompleteObject getComplete(int rawId) {
        return phoneContactReader.getComplete(rawId);
    }


    public ArrayList<ContentProviderResult[]> addContact(ArrayList<PhoneContactCompleteObject> phoneContactCompleteObjects) {
        return phoneContactInsert.addContactMultiple(phoneContactCompleteObjects);
    }

    public ArrayList<PhoneContactUserData> getOnlyUserDataForAllContactsByAccountName(
            PhoneContactAccountType phoneContactAccountType
    ) {

        return phoneContactReader.getAllUserDataForAccount(phoneContactAccountType);
    }
}
