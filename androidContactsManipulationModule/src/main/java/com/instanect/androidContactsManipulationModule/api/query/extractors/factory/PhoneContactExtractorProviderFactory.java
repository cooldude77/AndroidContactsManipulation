package com.instanect.androidContactsManipulationModule.api.query.extractors.factory;


import android.content.ContentResolver;

import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactDataExtractorInterface;

public class PhoneContactExtractorProviderFactory {

    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private ContentResolver contentResolver;

    public PhoneContactExtractorProviderFactory(PhoneContactSegmentProvider phoneContactSegmentProvider,
                                                ContentResolver contentResolver) {
        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public <T extends PhoneContactDataExtractorInterface> PhoneContactDataExtractorInterface getExtractor(
            Class<? extends PhoneContactDataExtractorInterface> tClass) {

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
