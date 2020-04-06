package com.instanect.androidContactsManipulationModule.structs.address

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface

class PhoneContactAddressData : PhoneContactSegmentInterface {
    var rawId: Int = -1
    var region: String? = ""
    var city: String? =  ""
    var country: String? = ""
    var state: String? = ""
    var postalCode: String? = ""
    var poBox: String? = ""
    var street: String? = ""
    var type: Int? = 0
}
