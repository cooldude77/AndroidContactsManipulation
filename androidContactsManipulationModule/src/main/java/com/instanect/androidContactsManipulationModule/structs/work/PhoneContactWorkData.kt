package com.instanect.androidContactsManipulationModule.structs.work

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface


class PhoneContactWorkData : PhoneContactSegmentInterface {

    var rawId: Int = -1;
    var department: String? = ""
    var company: String? = ""
    var jobTitle: String? = ""
}
