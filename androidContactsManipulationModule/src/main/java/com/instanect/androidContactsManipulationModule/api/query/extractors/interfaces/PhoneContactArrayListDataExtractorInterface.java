package com.instanect.androidContactsManipulationModule.api.query.extractors.interfaces;

import java.util.ArrayList;

public interface PhoneContactArrayListDataExtractorInterface<T extends PhoneContactDataExtractorInterface> {
    ArrayList<T> extract(int id);
}
