package pl.politechnika.ikms.commons.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static pl.politechnika.ikms.configuration.ApplicationConstants.*;


public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    /**
     * Serializer podczas przetwarzania na jsona z LocalDateTime jeśli dostaje na wejściu dzisiejszą
     * date to na wyjściu jest data w formacie "HH:mm".
     * W przeciwnym wypadku jest data w formacie "yyy-MM-dd HH:mm".
     * @param dateTime
     * @param jsonGenerator
     * @param serializer
     * @throws IOException
     * @throws JsonProcessingException
     */

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializer)
            throws IOException, JsonProcessingException {
        String stringDateTime = dateTime.format(DATE_FORMATTER);
        String stringNowDate = LocalDate.now().toString();

        if (stringDateTime.equals(stringNowDate)) {
            jsonGenerator.writeString(dateTime.format(FORMATTER_WITH_ONLY_TIME));
        } else {
            jsonGenerator.writeString(dateTime.format(FORMATTER_WITH_TIME));
        }
    }
}
