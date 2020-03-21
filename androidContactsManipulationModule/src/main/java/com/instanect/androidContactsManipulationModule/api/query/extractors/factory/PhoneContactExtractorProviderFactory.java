package com.instanect.androidContactsManipulationModule.api.query.extractors.factory;


import android.content.ContentResolver;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.factory.PhoneContactMapperProviderFactory;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces.PhoneContactMapperInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactDataExtractorInterface;

public class PhoneContactExtractorProviderFactory {

    private PhoneContactMapperProviderFactory phoneContactMapperProviderFactory;
    private ContentResolver contentResolver;

    public PhoneContactExtractorProviderFactory(
            PhoneContactMapperProviderFactory phoneContactMapperProviderFactory,
            ContentResolver contentResolver) {
        this.phoneContactMapperProviderFactory = phoneContactMapperProviderFactory;
        this.contentResolver = contentResolver;
    }

    public <T extends PhoneContactDataExtractorInterface> PhoneContactDataExtractorInterface getExtractor(
            Class<? extends PhoneContactDataExtractorInterface> classExtractor,
            Class<? extends PhoneContactMapperInterface> classMapper) {

        PhoneContactMapperInterface phoneContactMapperInterface
                = phoneContactMapperProviderFactory.getMapper(classMapper);
        Class[] cArg = new Class[2];
        cArg[0] = phoneContactMapperInterface.getClass();
        cArg[1] = ContentResolver.class;

        try {
            return classExtractor.getDeclaredConstructor(cArg).newInstance(
                    phoneContactMapperInterface,
                    contentResolver);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
