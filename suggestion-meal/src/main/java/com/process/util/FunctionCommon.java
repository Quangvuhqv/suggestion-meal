package com.process.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FunctionCommon {
    public static LocalTime convertStringToLocalTime(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(Constants.TIME_FORMAT));
    }
}
