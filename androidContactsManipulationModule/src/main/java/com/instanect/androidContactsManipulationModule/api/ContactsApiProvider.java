package com.instanect.androidContactsManipulationModule.api;

import android.content.Context;

public class ContactsApiProvider {

    public ContactsApi newInstance(Context context) {
        return new ContactsApi(context);
    }
}
