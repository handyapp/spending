package com.github.handyapp.spending.services;

import android.content.res.Resources;

public class BusinessService {

    protected static String getString(int id) {
        return Resources.getSystem().getString(id);
    }

}
