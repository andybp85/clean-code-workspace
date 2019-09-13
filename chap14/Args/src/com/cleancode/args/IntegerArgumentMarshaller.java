package com.cleancode.args;

import static com.cleancode.args.ArgsException.ErrorCode.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

class IntegerArgumentMarshaller implements ArgumentMarshaller {
    private Integer intValue;

    public void set(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            intValue = Integer.parseInt(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(MISSING_INTEGER, parameter);
        } catch (NumberFormatException e) {
            throw new ArgsException(INVALID_INTEGER, parameter);
        }
    }

    public static int geValue(ArgumentMarshaller am) {
        if (am != null && am instanceof IntegerArgumentMarshaller) {
            return ((IntegerArgumentMarshaller) am).intValue;
        } else {
            return 0;
        }
    }
}
