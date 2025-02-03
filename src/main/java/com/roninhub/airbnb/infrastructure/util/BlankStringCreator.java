package com.roninhub.airbnb.infrastructure.util;

public class BlankStringCreator {
    private static final String BLANK_STRING = createBlankString(10);

    private static String createBlankString(int size) {
        char[] chars = new char[size];
        for (int i = 0; i < size; i++) {
            chars[i] = ' ';
        }
        return new String(chars);
    }

    public static String getBlankString() {
        return BLANK_STRING;
    }
}
