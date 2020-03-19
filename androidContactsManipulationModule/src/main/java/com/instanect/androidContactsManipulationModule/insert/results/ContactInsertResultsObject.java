package com.instanect.androidContactsManipulationModule.insert.results;

import android.content.ContentProviderResult;
import android.net.Uri;

import com.instanect.androidContactsManipulationModule.structs.PhoneContactCompleteObject;

import java.util.ArrayList;

public class ContactInsertResultsObject {

    private Uri contactUri;
    private Uri nameDataUri;
    private Uri workDataUri;
    private ArrayList<Uri> phoneUri = new ArrayList<>();
    private ArrayList<Uri> emailUri = new ArrayList<>();

    public void mapUriData(PhoneContactCompleteObject phoneContactCompleteObject,
                           ContentProviderResult[] results) {

        int count = 0;
        contactUri = results[count++].uri;

        if (phoneContactCompleteObject.getPhoneContactNameData() != null)
            nameDataUri = results[count++].uri;
        if (phoneContactCompleteObject.getPhoneContactWorkData() != null)
            workDataUri = results[count++].uri;
        if (phoneContactCompleteObject.getPhoneContactEmailDataList() != null &&
                phoneContactCompleteObject.getPhoneContactEmailDataList().size() != 0) {
            for (int i = 0; i < phoneContactCompleteObject.getPhoneContactEmailDataList().size(); i++, count++)
                emailUri.add(results[count].uri);
            if (phoneContactCompleteObject.getPhoneContactPhoneDataList() != null &&
                    phoneContactCompleteObject.getPhoneContactPhoneDataList().size() != 0) {
                for (int i = 0; i < phoneContactCompleteObject.getPhoneContactPhoneDataList().size(); i++, count++)
                    phoneUri.add(results[count].uri);
            }

        }
    }
}