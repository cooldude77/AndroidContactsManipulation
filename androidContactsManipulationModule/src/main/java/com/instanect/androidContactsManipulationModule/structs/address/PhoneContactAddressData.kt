package com.instanect.androidContactsManipulationModule.structs.address

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface

class PhoneContactAddressData : PhoneContactSegmentInterface {
    var rawId: Int = -1
    var region: String? = null
    var city: String? = null
    var country: String? = null
    var state: String? = null
    var postalCode: String? = null
    var poBox: String? = null
    var street: String? = null
    var type: Int? = 0
}
