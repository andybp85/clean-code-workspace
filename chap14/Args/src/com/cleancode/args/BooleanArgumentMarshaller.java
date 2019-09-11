package com.cleancode.args;

import java.util.Iterator;

class BooleanArgumentMarshaller implements ArgumentMarshaller {
    private boolean booleanValue = false;

    public void set(Iterator<String> currentArgument) throws ArgsException {
        booleanValue = true;
    }

    public Object get() {
        return booleanValue;
    }
}
