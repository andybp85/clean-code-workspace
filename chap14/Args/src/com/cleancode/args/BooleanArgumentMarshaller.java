package com.cleancode.args;

import java.util.Iterator;

class BooleanArgumentMarshaller implements ArgumentMarshaller {
    private boolean booleanValue = false;

    public void set(Iterator<String> currentArgument) throws ArgsException {
        booleanValue = true;
    }

    public static boolean geValue(ArgumentMarshaller am) {
        if (am != null && am instanceof BooleanArgumentMarshaller) {
            return ((BooleanArgumentMarshaller) am).booleanValue;
        } else {
            return false;
        }
    }
}
