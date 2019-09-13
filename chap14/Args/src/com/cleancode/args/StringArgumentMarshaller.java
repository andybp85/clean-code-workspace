package com.cleancode.args;

import static com.cleancode.args.ArgsException.ErrorCode.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

class StringArgumentMarshaller implements ArgumentMarshaller {
    private String stringValue = " ";

    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            stringValue = currentArgument.next();
        } catch (NoSuchElementException e) {
            throw new ArgsException(MISSING_STRING, stringValue);
        }
    }

    public static String geValue(ArgumentMarshaller am) {
        if (am != null && am instanceof StringArgumentMarshaller) {
            return ((StringArgumentMarshaller) am).stringValue;
        } else {
            return "";
        }
    }
}
