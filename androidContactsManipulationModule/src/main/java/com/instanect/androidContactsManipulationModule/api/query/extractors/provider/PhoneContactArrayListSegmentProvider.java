package com.instanect.androidContactsManipulationModule.api.query.extractors.provider;

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface;

import java.util.ArrayList;

public class PhoneContactArrayListSegmentProvider {

    public <T extends PhoneContactSegmentInterface> ArrayList<T> newInstance(
            Class<? extends PhoneContactSegmentInterface> tClass) {

        try {
            return new ArrayList<T>();
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
