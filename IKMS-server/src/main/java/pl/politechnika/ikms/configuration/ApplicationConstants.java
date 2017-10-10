package pl.politechnika.ikms.configuration;

import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

public final class ApplicationConstants {
    public static final DateTimeFormatter FORMATTER = ofPattern("yyy-MM-dd");
}
