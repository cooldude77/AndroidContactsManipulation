package com.instanect.androidContactsManipulationModule.structs

import com.instanect.androidContactsManipulationModule.structs.accountType.PhoneContactAccountType
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactEmailData
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactPhoneData
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactNameData
import com.instanect.androidContactsManipulationModule.structs.work.PhoneContactWorkData

class PhoneContactCompleteObject {
    var phoneContactAccountType: PhoneContactAccountType? = null
    var phoneContactNameData: PhoneContactNameData? = null
    var phoneContactWorkData: PhoneContactWorkData? = null
    var phoneContactPhoneDataList: ArrayList<PhoneContactPhoneData>? = null
    var phoneContactEmailDataList: ArrayList<PhoneContactEmailData>? = null
}