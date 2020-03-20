package com.instanect.androidContactsManipulationModule.structs.notes

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface

class PhoneContactNoteData : PhoneContactSegmentInterface {
    var idAndroid: Int = 0
    var note: String? = null
}
