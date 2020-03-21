package com.instanect.androidContactsManipulationModule.api.query.extractors.factory;

import android.content.ContentResolver;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.factory.PhoneContactMapperIntoArrayListProviderFactory;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.factory.PhoneContactMapperProviderFactory;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces.PhoneContactMapperArrayListInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;

public class PhoneContactExtractorIntoArrayListProviderFactory {

    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;
    private final PhoneContactMapperProviderFactory phoneContactMapperProviderFactory;
    private final PhoneContactMapperIntoArrayListProviderFactory phoneContactMapperIntoArrayListProviderFactory;
    private ContentResolver contentResolver;

    public PhoneContactExtractorIntoArrayListProviderFactory(
            PhoneContactMapperProviderFactory phoneContactMapperProviderFactory,
            PhoneContactMapperIntoArrayListProviderFactory phoneContactMapperIntoArrayListProviderFactory,
            ContentResolver contentResolver) {
        this.phoneContactMapperProviderFactory = phoneContactMapperProviderFactory;
        this.phoneContactMapperIntoArrayListProviderFactory = phoneContactMapperIntoArrayListProviderFactory;
        this.contentResolver = contentResolver;
    }

    public <T extends PhoneContactArrayListDataExtractorInterface>
    PhoneContactArrayListDataExtractorInterface
    getExtractor(
            Class<? extends PhoneContactArrayListDataExtractorInterface> extractorClass,
            Class<? extends PhoneContactMapperArrayListInterface> mapperClass) {

        PhoneContactMapperArrayListInterface
                phoneContactMapperArrayListInterface
                = phoneContactMapperIntoArrayListProviderFactory.getMapper(
                mapperClass
        );

        Class[] cArg = new Class[2];
        cArg[0] = mapperClass.getClass();
        cArg[1] = PhoneContactArrayListSegmentProvider.class;

        try {
            return extractorClass.getDeclaredConstructor(cArg).newInstance(
                    phoneContactMapperArrayListInterface,
                    contentResolver);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
