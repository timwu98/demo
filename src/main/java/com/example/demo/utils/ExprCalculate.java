package com.example.demo.utils;

public class ExprCalculate {
    private static String expr;
    private static int pos;

    public static long calculate(String expression) {
        expr = expression;
        pos = 0;
        long result = parseExpr();
        skipSpaces();
        if (pos != expr.length()) {
            throw new IllegalArgumentException("Unexpected input at position " + pos);
        }
        return result;
    }

    private static long parseExpr() {
        long v = parseTerm();
        while (true) {
            skipSpaces();
            if (match('+')) {
                v += parseTerm();
            }
            else if (match('-')) {
                v -= parseTerm();
            }
            else {
                return v;
            }
        }
    }

    private static long parseTerm() {
        long v = parseFactor();
        while (true) {
            skipSpaces();
            if (match('*')) {
                v *= parseFactor();
            }
            else if (match('/')) {
                long r = parseFactor();
                if (r == 0) {
                    throw new IllegalArgumentException("Division by zero");
                }
                v /= r;
            }
            else {
                return v;
            }
        }
    }

    private static long parseFactor() {
        skipSpaces();
        if (match('+')) {
            return +parseFactor();
        }
        if (match('-')) {
            return -parseFactor();
        }
        if (match('(')) {
            long in = parseExpr();
            if (!match(')')) {
                throw new IllegalArgumentException("Missing ')'");
            }
            return in;
        }
        return parseInt();
    }

    private static long parseInt() {
        skipSpaces();
        if (pos >= expr.length() || !Character.isDigit(expr.charAt(pos)))
            throw new IllegalArgumentException("Integer expected at position " + pos);
        long v = 0;
        while (pos < expr.length() && Character.isDigit(expr.charAt(pos))) {
            v = v * 10 + (expr.charAt(pos) - '0');
            pos++;
        }
        return v;
    }

    private static void skipSpaces() {
        while (pos < expr.length() && Character.isWhitespace(expr.charAt(pos))) {
            pos++;
        }
    }

    private static boolean match(char c) {
        if (pos < expr.length() && expr.charAt(pos) == c) {
            pos++;return true;
        }
        return false;
    }
}