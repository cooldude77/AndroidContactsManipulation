package com.instanect.androidContactsManipulationModule.structs.notes

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface

class PhoneContactNoteData : PhoneContactSegmentInterface {
    var id: Int = -1
    var note: String? = null
}
