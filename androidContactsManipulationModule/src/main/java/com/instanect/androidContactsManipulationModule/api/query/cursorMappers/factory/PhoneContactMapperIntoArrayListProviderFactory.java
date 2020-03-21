package com.instanect.androidContactsManipulationModule.api.query.cursorMappers.factory;

import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces.PhoneContactMapperArrayListInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;

public class PhoneContactMapperIntoArrayListProviderFactory {

    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;

    public PhoneContactMapperIntoArrayListProviderFactory(
            PhoneContactSegmentProvider phoneContactSegmentProvider,
            PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider) {
        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
    }

    public <T extends PhoneContactMapperArrayListInterface> PhoneContactMapperArrayListInterface
    getMapper(Class<? extends PhoneContactMapperArrayListInterface> tClass) {

        Class[] cArg = new Class[2];
        cArg[0] = PhoneContactSegmentProvider.class;
        cArg[1] = PhoneContactArrayListSegmentProvider.class;

        try {
            return tClass.getDeclaredConstructor(cArg).newInstance(
                    phoneContactSegmentProvider,
                    phoneContactArrayListSegmentProvider);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
