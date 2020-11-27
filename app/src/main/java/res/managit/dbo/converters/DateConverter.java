package res.managit.dbo.converters;


import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateConverter {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm    dd-MM-yyyy");

    @TypeConverter
    public static String dateToString(LocalDateTime d) {
        return d.format(formatter);
    }

    @TypeConverter
    public static LocalDateTime stringToDate(String str) {
        return LocalDateTime.parse(str, formatter);
    }
}