package com.instanect.androidContactsManipulationModule.api.query.cursorMappers.interfaces;

import android.database.Cursor;

import java.util.ArrayList;

public interface PhoneContactMapperArrayListInterface<T extends PhoneContactMapperInterface> {
    public ArrayList<T> map(Cursor cursor);
}
