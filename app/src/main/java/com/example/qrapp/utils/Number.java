package com.example.qrapp.utils;

import java.text.DecimalFormat;

public class Number {

    public static String currency(long value) {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(value);
    }
}
