package pl.politechnika.ikms.configuration;

import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

public final class ApplicationConstants {
    public static final DateTimeFormatter DATE_FORMATTER = ofPattern("yyy-MM-dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = ofPattern("yyy-MM-dd HH:mm:ss");

    public static final String ROLE_CACHE = "role";
}
