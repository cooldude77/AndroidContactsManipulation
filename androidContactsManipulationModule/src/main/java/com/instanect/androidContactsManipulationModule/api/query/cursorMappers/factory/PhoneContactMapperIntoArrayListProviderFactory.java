package com.instanect.androidContactsManipulationModule.api.query.cursorMappers.factory;

import android.content.ContentResolver;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces.PhoneContactMapperArrayListInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;

public class PhoneContactMapperIntoArrayListProviderFactory {

    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;
    private ContentResolver contentResolver;

    public PhoneContactMapperIntoArrayListProviderFactory(
            PhoneContactSegmentProvider phoneContactSegmentProvider,
            PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider,
            ContentResolver contentResolver) {
        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public <T extends PhoneContactMapperArrayListInterface> PhoneContactMapperArrayListInterface
    getMapper(Class<? extends PhoneContactMapperArrayListInterface> tClass) {

        Class[] cArg = new Class[3];
        cArg[0] = PhoneContactSegmentProvider.class;
        cArg[1] = PhoneContactArrayListSegmentProvider.class;
        cArg[2] = ContentResolver.class;

        try {
            return tClass.getDeclaredConstructor(cArg).newInstance(
                    phoneContactSegmentProvider,
                    phoneContactArrayListSegmentProvider,
                    contentResolver);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
