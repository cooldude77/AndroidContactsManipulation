package com.instanect.androidContactsManipulationModule.api.query.extractors.factory;

import android.content.ContentResolver;

import com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces.PhoneContactArrayListDataExtractorInterface;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;

public class PhoneContactExtractorIntoArrayListProviderFactory {

    private PhoneContactSegmentProvider phoneContactSegmentProvider;
    private PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider;
    private ContentResolver contentResolver;

    public PhoneContactExtractorIntoArrayListProviderFactory(PhoneContactSegmentProvider phoneContactSegmentProvider,
                                                             PhoneContactArrayListSegmentProvider phoneContactArrayListSegmentProvider,
                                                             ContentResolver contentResolver) {
        this.phoneContactSegmentProvider = phoneContactSegmentProvider;
        this.phoneContactArrayListSegmentProvider = phoneContactArrayListSegmentProvider;
        this.contentResolver = contentResolver;
    }

    public <T extends PhoneContactArrayListDataExtractorInterface>
    PhoneContactArrayListDataExtractorInterface
    getExtractor(Class<? extends PhoneContactArrayListDataExtractorInterface> tClass) {

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
