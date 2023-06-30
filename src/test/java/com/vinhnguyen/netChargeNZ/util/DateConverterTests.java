package com.vinhnguyen.netChargeNZ.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DateConverterTests {
    @Test
    public void testConvertDateTimeToString() {
        DateConverter dateConverter = new DateConverter();

        LocalDateTime dateTime = LocalDateTime.of(2023, 6, 29, 20, 0);
        String formattedDateTime = dateConverter.convertDateTimeToString(dateTime);

        Assertions.assertEquals("2023/06/29 20:00", formattedDateTime);
    }

    @Test
    public void testConvertDateTimeToStringWithNullInput() {
        DateConverter dateConverter = new DateConverter();

        String formattedDateTime = dateConverter.convertDateTimeToString(null);

        Assertions.assertNull(formattedDateTime);
    }

    @Test
    public void testConvertStringToDateTime() {
        DateConverter dateConverter = new DateConverter();

        String datetime = "2023/06/29 20:00";
        LocalDateTime parsedDateTime = dateConverter.convertStringToDateTime(datetime);

        Assertions.assertEquals(LocalDateTime.of(2023, 6, 29, 20, 0), parsedDateTime);
    }

    @Test
    public void testConvertStringToDateTime_WithHyphensInput() {
        DateConverter dateConverter = new DateConverter();

        String datetime = "2023-06-29 20:00";
        LocalDateTime parsedDateTime = dateConverter.convertStringToDateTime(datetime);

        Assertions.assertEquals(LocalDateTime.of(2023, 6, 29, 20, 0), parsedDateTime);
    }

    @Test
    public void testConvertStringToDateTime_WithDateOnly() {
        DateConverter dateConverter = new DateConverter();

        String datetime = "2023/06/29";
        LocalDateTime parsedDateTime = dateConverter.convertStringToDateTime(datetime);

        Assertions.assertEquals(LocalDateTime.of(2023, 6, 29, 0, 0), parsedDateTime);
    }

    @Test
    public void testConvertStringToDateTime_WithDifferentFormats() {
        DateConverter dateConverter = new DateConverter();

        String datetime1 = "29 Jun 2023";
        LocalDateTime parsedDateTime1 = dateConverter.convertStringToDateTime(datetime1);
        Assertions.assertEquals(LocalDateTime.of(2023, 6, 29, 0, 0), parsedDateTime1);

        String datetime2 = "Jun 29 2023";
        LocalDateTime parsedDateTime2 = dateConverter.convertStringToDateTime(datetime2);
        Assertions.assertEquals(LocalDateTime.of(2023, 6, 29, 0, 0), parsedDateTime2);

        String datetime3 = "2023 Jun 29";
        LocalDateTime parsedDateTime3 = dateConverter.convertStringToDateTime(datetime3);
        Assertions.assertEquals(LocalDateTime.of(2023, 6, 29, 0, 0), parsedDateTime3);

        String datetime4 = "2023 29 June";
        LocalDateTime parsedDateTime4 = dateConverter.convertStringToDateTime(datetime4);
        Assertions.assertEquals(LocalDateTime.of(2023, 6, 29, 0, 0), parsedDateTime4);

        String datetime5 = "2023-June-29";
        LocalDateTime parsedDateTime5 = dateConverter.convertStringToDateTime(datetime5);
        Assertions.assertEquals(LocalDateTime.of(2023, 6, 29, 0, 0), parsedDateTime5);

        String datetime6 = "2023/06/29";
        LocalDateTime parsedDateTime6 = dateConverter.convertStringToDateTime(datetime6);
        Assertions.assertEquals(LocalDateTime.of(2023, 6, 29, 0, 0), parsedDateTime6);

        String datetime7 = "06/29/2023";
        LocalDateTime parsedDateTime7 = dateConverter.convertStringToDateTime(datetime7);
        Assertions.assertEquals(LocalDateTime.of(2023, 6, 29, 0, 0), parsedDateTime7);
    }

    @Test
    public void testConvertStringToDateTime_InvalidFormat() {
        DateConverter dateConverter = new DateConverter();

        String datetime = "203-06-29Hello";

        Assertions.assertThrows(NullPointerException.class, () -> {
            dateConverter.convertStringToDateTime(datetime);
        });
    }
}
