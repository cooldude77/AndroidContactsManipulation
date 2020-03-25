package com.instanect.androidContactsManipulationModule.common;

import android.content.Context;

import com.instanect.androidContactsManipulationModule.api.ContactsApiProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsApiModule {

    @Provides
    ContactsApiProvider provideContactsApiProvider() {

        return new ContactsApiProvider();
    }
}
