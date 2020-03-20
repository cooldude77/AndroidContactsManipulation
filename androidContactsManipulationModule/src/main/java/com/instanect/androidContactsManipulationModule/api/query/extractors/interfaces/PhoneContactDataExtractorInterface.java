package com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces;


public interface PhoneContactDataExtractorInterface<T extends PhoneContactDataExtractorInterface> {
    <U extends PhoneContactSegmentInterface> U extract(int id);
}
