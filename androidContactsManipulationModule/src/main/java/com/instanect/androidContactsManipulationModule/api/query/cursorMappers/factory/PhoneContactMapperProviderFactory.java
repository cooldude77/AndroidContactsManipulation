package com.instanect.androidContactsManipulationModule.api.query.cursorMappers.factory;


import android.content.ContentResolver;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces.PhoneContactMapperInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;

public class PhoneContactMapperProviderFactory {

    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private ContentResolver contentResolver;

    public PhoneContactMapperProviderFactory(PhoneContactSegmentProvider phoneContactSegmentProvider,
                                                ContentResolver contentResolver) {
        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public <T extends PhoneContactMapperInterface> PhoneContactMapperInterface getMapper(
            Class<? extends PhoneContactMapperInterface> tClass) {

        Class[] cArg = new Class[2];
        cArg[0] = PhoneContactSegmentProvider.class;
        cArg[1] = ContentResolver.class;

        try {
            return tClass.getDeclaredConstructor(cArg).newInstance(
                    phoneContactSegmentProvider,
                    contentResolver);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
