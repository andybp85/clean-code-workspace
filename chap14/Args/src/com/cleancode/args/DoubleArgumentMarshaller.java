package com.cleancode.args;

import static com.cleancode.args.ArgsException.ErrorCode.*;
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
            throw new ArgsException(MISSING_DOUBLE, parameter);
        } catch (NumberFormatException e) {
            throw new ArgsException(INVALID_DOUBLE, parameter);
        }
    }

    public static double geValue(ArgumentMarshaller am) {
        if (am != null && am instanceof DoubleArgumentMarshaller) {
            return ((DoubleArgumentMarshaller) am).doubleValue;
        } else {
            return 0.0;
        }
    }
}
