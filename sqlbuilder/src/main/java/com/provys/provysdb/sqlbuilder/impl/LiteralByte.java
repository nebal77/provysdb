package com.provys.provysdb.sqlbuilder.impl;

import javax.annotation.Nonnull;

public class LiteralByte extends LiteralNumber<Byte> {

    /**
     * Get literal corresponding to given Byte value
     *
     * @param value is byte value this literal represents
     */
    @Nonnull
    public static LiteralByte of(byte value) {
        return new LiteralByte(value);
    }

    private LiteralByte(Byte value) {
        super(value);
    }
}