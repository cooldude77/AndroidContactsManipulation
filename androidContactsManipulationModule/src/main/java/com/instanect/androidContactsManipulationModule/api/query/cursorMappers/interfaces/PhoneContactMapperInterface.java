package com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces;

import android.database.Cursor;

import com.instanect.androidContactsManipulationModule.api.query.interfaces.PhoneContactSegmentInterface;

public interface PhoneContactMapperInterface {
    <T extends PhoneContactSegmentInterface> T map(Cursor cursor);
}
