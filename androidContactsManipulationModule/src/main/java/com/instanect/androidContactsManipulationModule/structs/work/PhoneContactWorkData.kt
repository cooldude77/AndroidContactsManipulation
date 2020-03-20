package com.instanect.androidContactsManipulationModule.structs.work

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface


class PhoneContactWorkData : PhoneContactSegmentInterface {

    var department: String? = null
    var idPhoneWorkData = -1
    var company: String? = null
    var jobTitle: String? = null
}
