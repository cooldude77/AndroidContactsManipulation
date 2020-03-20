package com.instanect.androidContactsManipulationModule.api.query.extractors.provider;

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface;

public class PhoneContactSegmentProvider {

    public <T extends PhoneContactSegmentInterface> PhoneContactSegmentInterface
    newInstance(Class<? extends PhoneContactSegmentInterface> tClass) {

        try {
            return tClass.newInstance();
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
