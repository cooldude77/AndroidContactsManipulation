package com.instanect.androidContactsManipulationModule.structs.user


import android.net.Uri
import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface

class PhoneContactUserData : PhoneContactSegmentInterface {

    var rawContactId: Int = -1 // id of top record
    var displayName: String? = ""
    var firstName: String? = ""
    var middleName: String? = ""
    var lastName: String? = ""
    var prefix: String? = ""
    var suffix: String? = ""
    var rawId = -1 // id of this record
    var photoUri: Uri? = null
}
