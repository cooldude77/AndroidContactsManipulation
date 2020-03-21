package com.instanect.androidContactsManipulationModule.api;

import android.content.Context;

import com.instanect.androidContactsManipulationModule.api.insert.PhoneContactInsert;
import com.instanect.androidContactsManipulationModule.api.query.PhoneContactCursor;
import com.instanect.androidContactsManipulationModule.api.query.PhoneContactObjectProvider;
import com.instanect.androidContactsManipulationModule.api.query.PhoneContactReader;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.factory.PhoneContactMapperIntoArrayListProviderFactory;
import com.instanect.androidContactsManipulationModule.api.query.cursorMappers.factory.PhoneContactMapperProviderFactory;
import com.instanect.androidContactsManipulationModule.api.query.extractors.extractor.main.PhoneContactExtractorMain;
import com.instanect.androidContactsManipulationModule.api.query.extractors.factory.PhoneContactExtractorIntoArrayListProviderFactory;
import com.instanect.androidContactsManipulationModule.api.query.extractors.factory.PhoneContactExtractorProviderFactory;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactArrayListSegmentProvider;
import com.instanect.androidContactsManipulationModule.api.query.extractors.provider.PhoneContactSegmentProvider;

public class ContactsApiProvider {

    public ContactsApi newInstance(Context context) {
        return new ContactsApi(
                context,
                new PhoneContactReader(
                        new PhoneContactCursor(context.getContentResolver()),
                        new PhoneContactExtractorMain(
                                new PhoneContactObjectProvider(),
                                new PhoneContactExtractorProviderFactory(
                                        new PhoneContactMapperProviderFactory(
                                                new PhoneContactSegmentProvider()
                                        ),
                                        context.getContentResolver()
                                ),
                                new PhoneContactExtractorIntoArrayListProviderFactory(
                                        new PhoneContactMapperProviderFactory(
                                                new PhoneContactSegmentProvider()
                                        ),
                                        new PhoneContactMapperIntoArrayListProviderFactory(
                                                new PhoneContactSegmentProvider(),
                                                new PhoneContactArrayListSegmentProvider()
                                        ),
                                        context.getContentResolver()
                                )
                        ),
                        context
                ),
                new PhoneContactInsert(context)
        );
    }
}
