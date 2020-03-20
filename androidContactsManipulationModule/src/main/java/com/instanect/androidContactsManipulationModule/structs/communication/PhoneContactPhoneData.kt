package com.instanect.androidContactsManipulationModule.structs.communication

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface


class PhoneContactPhoneData : PhoneContactSegmentInterface {

    var idRaw: Int = -1
    var idPhoneData: Int = 0
    var phoneNumber: String? = null
    var phoneType: Int = 0
}
