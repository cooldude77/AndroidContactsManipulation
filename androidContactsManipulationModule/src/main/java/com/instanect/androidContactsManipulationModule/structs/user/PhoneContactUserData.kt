package com.instanect.androidContactsManipulationModule.structs.user


import android.net.Uri
import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface

class PhoneContactUserData : PhoneContactSegmentInterface {

    var displayName: String? = ""
    var firstName: String? = ""
    var middleName: String? = ""
    var lastName: String? = ""
    var prefix: String? = ""
    var suffix: String? = ""
    var rawId = -1
    var photoUri: Uri? = null
}
