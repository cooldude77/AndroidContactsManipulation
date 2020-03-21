package com.instanect.androidContactsManipulationModule.api.query.cursorMappers.factory;


import android.content.ContentResolver;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces.PhoneContactMapperInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;

public class PhoneContactMapperProviderFactory {

    private PhoneContactSegmentProvider phoneContactSegmentProvider;

    public PhoneContactMapperProviderFactory(PhoneContactSegmentProvider phoneContactSegmentProvider) {
        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
    }

    public <T extends PhoneContactMapperInterface> PhoneContactMapperInterface getMapper(
            Class<? extends PhoneContactMapperInterface> tClass) {

        Class[] cArg = new Class[1];
        cArg[0] = PhoneContactSegmentProvider.class;

        try {
            return tClass.getDeclaredConstructor(cArg).newInstance(phoneContactSegmentProvider);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
