package com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces;


import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface;

public interface PhoneContactDataExtractorInterface<T extends PhoneContactDataExtractorInterface> {
    <U extends PhoneContactSegmentInterface> U extract(int id);
}
