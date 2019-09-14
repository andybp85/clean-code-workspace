package com.cleancode.args;

import java.util.*;

import static com.cleancode.args.ArgsException.ErrorCode.*;

class StringArrayArgumentMarshaller implements ArgumentMarshaller {
    private List<String> strings = new ArrayList<String>();

    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            strings.add(currentArgument.next());
        } catch (NoSuchElementException e) {
            throw new ArgsException(MISSING_STRING_ARRAY, strings.toString());
        }
    }

    public static String[] getValue(ArgumentMarshaller am) {
        if (am != null && am instanceof StringArrayArgumentMarshaller)
            return ((StringArrayArgumentMarshaller) am).strings.toArray(new String[0]);
        else
            return new String[0];
    }
}