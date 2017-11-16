package pl.politechnika.ikms.configuration;

import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

public final class ApplicationConstants {
    public static final DateTimeFormatter FORMATTER = ofPattern("yyy-MM-dd");

    public static final DateTimeFormatter FORMATTER_WITH_TIME = ofPattern("yyy-MM-dd HH:mm");

    public static final DateTimeFormatter FORMATTER_WITH_ONLY_TIME = ofPattern("HH:mm");

    public static final String ROLE_CACHE = "role";
}
