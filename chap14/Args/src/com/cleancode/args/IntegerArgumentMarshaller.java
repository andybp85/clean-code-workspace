package com.cleancode.args;

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
            throw new ArgsException(ArgsException.ErrorCode.MISSING_INTEGER, parameter);
        } catch (NumberFormatException e) {
            throw new ArgsException(ArgsException.ErrorCode.INVALID_INTEGER, parameter);
        }
    }

    public Integer get() {
        return intValue;
    }
}
