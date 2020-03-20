package com.instanect.androidContactsManipulationModule.structs.communication

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface

class PhoneContactWebData  : PhoneContactSegmentInterface {
    var idAndroid: Int = 0
    var url: String? = null
    var urlType: Int = 0
}
