package com.cleancode.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

class DoubleArgumentMarshaller implements ArgumentMarshaller {
    private double doubleValue = 0;

    public void set(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            doubleValue = Double.parseDouble(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(ArgsException.ErrorCode.MISSING_DOUBLE, parameter);
        } catch (NumberFormatException e) {
            throw new ArgsException(ArgsException.ErrorCode.INVALID_DOUBLE, parameter);
        }
    }

    public Object get() {
        return doubleValue;
    }
}
