package com.instanect.androidContactsManipulationModule.structs.work

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface


class PhoneContactWorkData : PhoneContactSegmentInterface {

    var id: Int = -1;
    var department: String? = null
    var company: String? = null
    var jobTitle: String? = null
}
