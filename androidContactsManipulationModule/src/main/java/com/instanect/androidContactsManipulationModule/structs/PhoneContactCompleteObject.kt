package com.instanect.androidContactsManipulationModule.structs

import com.instanect.androidContactsManipulationModule.structs.accountType.PhoneContactAccountType
import com.instanect.androidContactsManipulationModule.structs.address.PhoneContactAddressData
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactEmailData
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactPhoneData
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactWebData
import com.instanect.androidContactsManipulationModule.structs.notes.PhoneContactNoteData
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactUserData
import com.instanect.androidContactsManipulationModule.structs.work.PhoneContactWorkData

class PhoneContactCompleteObject {
    var phoneContactAccountType: PhoneContactAccountType? = PhoneContactAccountType()
    var phoneContactNameData = PhoneContactUserData()
    var phoneContactWorkData = PhoneContactWorkData()
    var phoneContactPhoneDataList: ArrayList<PhoneContactPhoneData>? = ArrayList()
    var phoneContactEmailDataList: ArrayList<PhoneContactEmailData>? = ArrayList()
    var phoneContactWebDataList: ArrayList<PhoneContactWebData>? = ArrayList()
    var phoneContactAddressDataList: ArrayList<PhoneContactAddressData> = ArrayList();
    var phoneContactNoteDataList: ArrayList<PhoneContactNoteData>? = ArrayList()
}