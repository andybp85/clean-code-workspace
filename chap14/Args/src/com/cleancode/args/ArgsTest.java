package com.cleancode.args;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgsTest {

    @Test
    public void testSimpleDoublePresent() throws Exception {
        Args args = new Args("x##", new String[] {"-x", "42.3"});
        assertTrue(args.isValid());
        assertEquals(1, args.cardinality());
        assertTrue(args.has('x'));
        assertEquals(42.3, args.getDouble('x'), .001);
    }

}