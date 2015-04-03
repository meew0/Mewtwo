package meew0.mewtwo.core;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Created by meew0 on 01.04.15.
 */
public class MewtwoLogger {
    public static final String reset = "\u001B[0m",
            black = "\u001B[30m",
            red = "\u001B[31m",
            green = "\u001B[32m",
            yellow = "\u001B[33m",
            blue = "\u001B[34m",
            purple = "\u001B[35m",
            cyan = "\u001B[36m",
            white = "\u001B[37m",
            bold = "\u001B[1m",
            none = "";

    public static final String outcomingArrow = "→",
            incomingArrow = "←",
            cross = "✗",
            exclamation = "!",
            info = " ",
            checkMark = "✓";

    public static void outcoming(String data) {
        log(outcomingArrow, cyan, data);
    }

    public static void incoming(String data) {
        log(incomingArrow, purple, data);
    }

    public static void error(String data) {
        log(cross, red, data);
    }

    public static void errorThrowable(Throwable t) {
        for (String line : ExceptionUtils.getStackTrace(t).split("\n")) {
            error(line);
        }
    }

    public static void warn(String data) {
        log(exclamation, yellow, data);
    }

    public static void info(String data) {
        log(info, none, data);
    }

    public static void good(String data) {
        log(checkMark, green, data);
    }

    public static void log(String symbol, String symbolColor, String data) {
        System.out.println(bold + getPaddedThreadName() + reset + symbolColor + symbol + reset + " " + data);
    }

    private static String getPaddedThreadName() {
        return String.format("%-15s", Thread.currentThread().getName());
    }
}
