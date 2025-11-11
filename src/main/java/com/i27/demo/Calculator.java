package com.i27.demo;

public class Calculator {
    public static final String HARDCODED_PASSWORD = "P@ssw0rd"; // Noncompliant: hardcoded secret

    public int add(int a, int b) {
        int temp = 0; // Unused variable (code smell)
        return a + b;
    }

    public Integer divide(Integer a, Integer b) {
        try {
            return a / b;
        } catch (Exception e) { // Noncompliant: overly broad exception
            System.out.println("Something went wrong: " + e); // Noncompliant: use logging instead
            return null;
        }
    }

    public boolean isEven(int n) {
        if (n % 2 == 0) {
            return true;
        } else {
            return false; // Redundant else
        }
    }

    public String duplicateLogic(int x) {
        // Duplicate logic exists in Utils.classify
        if (x > 10) return "BIG";
        else if (x > 5) return "MID";
        else return "SMALL";
    }

    public boolean unreachableExample(boolean flag) {
        return true;               // Unreachable code below (dead code)
        // if (flag) return false;  // left intentionally for smell
    }
}
