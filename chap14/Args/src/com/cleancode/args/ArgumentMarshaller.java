package com.cleancode.args;

import java.util.Iterator;

interface ArgumentMarshaller {
    void set(Iterator<String> currentArgument) throws ArgsException;
    Object get();
}
