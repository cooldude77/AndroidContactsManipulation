package com.instanect.androidContactsManipulationModule.structs.user


import android.net.Uri
import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface

class PhoneContactUserData : PhoneContactSegmentInterface {

    var displayName: String? = null
    var firstName: String? = null
    var middleName: String? = null
    var lastName: String? = null
    var prefix: String? = null
    var suffix: String? = null
    var rawId = -1

}
