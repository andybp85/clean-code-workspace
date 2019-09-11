package com.cleancode.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

class StringArgumentMarshaller implements ArgumentMarshaller {
    private String stringValue = " ";

    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            stringValue = currentArgument.next();
        } catch (NoSuchElementException e) {
            throw new ArgsException(ArgsException.ErrorCode.MISSING_STRING, stringValue);
        }
    }

    public String get() {
        return stringValue == null ? " " : stringValue;
    }
}
