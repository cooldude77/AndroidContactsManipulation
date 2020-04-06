package com.instanect.androidContactsManipulationModule.structs.communication

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface

class PhoneContactEmailData : PhoneContactSegmentInterface {

    var rawId: Int = -1
    var email: String? = ""
    var emailType: Int = 0
}
