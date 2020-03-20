package com.instanect.androidContactsManipulationModule.structs

import com.instanect.androidContactsManipulationModule.structs.accountType.PhoneContactAccountType
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactEmailData
import com.instanect.androidContactsManipulationModule.structs.communication.PhoneContactPhoneData
import com.instanect.androidContactsManipulationModule.structs.user.PhoneContactUserData
import com.instanect.androidContactsManipulationModule.structs.work.PhoneContactWorkData

class PhoneContactCompleteObject {
    var phoneContactAccountType: PhoneContactAccountType? = null
    var phoneContactNameData: PhoneContactUserData? = null
    var phoneContactWorkData: PhoneContactWorkData? = null
    var phoneContactPhoneDataList: ArrayList<PhoneContactPhoneData>? = ArrayList()
    var phoneContactEmailDataList: ArrayList<PhoneContactEmailData>? = ArrayList()
}