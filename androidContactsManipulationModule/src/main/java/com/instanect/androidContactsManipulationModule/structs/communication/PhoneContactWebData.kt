package com.instanect.androidContactsManipulationModule.structs.communication

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface

class PhoneContactWebData : PhoneContactSegmentInterface {
    var rawId: Int = -1
    var url: String? = ""
    var urlType: Int = 0
}
