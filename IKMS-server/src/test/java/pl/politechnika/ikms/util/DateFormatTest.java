package pl.politechnika.ikms.util;

import org.junit.Test;
import pl.politechnika.ikms.commons.serializers.LocalDateConverter;
import pl.politechnika.ikms.commons.serializers.LocalDateTimeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateFormatTest {

    @Test
    public void localDateTimeTest(){
        LocalDateTime datetime = LocalDateTime.now();
        LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
        System.out.println(localDateTimeConverter.convertToDatabaseColumn(datetime));
    }

    @Test
    public void localDateTest(){
        LocalDate date = LocalDate.now();
        LocalDateConverter localDateConverter = new LocalDateConverter();
        System.out.println(localDateConverter.convertToDatabaseColumn(date));
    }
}
