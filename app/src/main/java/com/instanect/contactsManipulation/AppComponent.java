package com.instanect.contactsManipulation;

import com.instanect.androidContactsManipulationModule.common.ContactsApiModule;

import dagger.Component;
import dagger.Subcomponent;

@Component(modules = {ContactsApiModule.class})
public interface AppComponent {


    void inject(MainActivity mainActivity);
}
