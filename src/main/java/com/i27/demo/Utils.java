package com.i27.demo;

public class Utils {
    // Intentionally similar to Calculator.duplicateLogic to trigger duplication
    public static String classify(int x) {
        if (x > 10) return "BIG";
        else if (x > 5) return "MID";
        else return "SMALL";
    }
}
