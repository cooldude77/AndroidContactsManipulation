package com.instanect.androidContactsManipulationModule.structs.communication

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface


class PhoneContactPhoneData : PhoneContactSegmentInterface {

    var rawId: Int = -1
    var phoneNumber: String? = ""
    var phoneType: Int = 0
}
