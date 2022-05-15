package com.photoapp.auth.utility;

import org.joda.time.IllegalInstantException;

public class Utility {


    private Utility() {
        throw new IllegalInstantException("Can't instantiate Utility class");
    }

    public static boolean notEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean notEmpty(Object obj) {
        return obj!=null;
    }
}
