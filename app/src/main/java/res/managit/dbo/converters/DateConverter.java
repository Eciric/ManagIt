package res.managit.dbo.converters;


import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class created to convert LocalDataTime to String and vice versa
 * It is needed for correct saving of LocalDataTime variables in Room database tables
 */
public class DateConverter {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm    dd-MM-yyyy");

    /**
     * Function converting LocalDatatime to String
     *
     * @param d Date and time represented with LocalDataTime variable
     * @return String, correct with formatter, with date and time from d variable
     */
    @TypeConverter
    public static String dateToString(LocalDateTime d) {
        return d.format(formatter);
    }

    /**
     * Function converting String to LocalDatatime
     *
     * @param str Date and time represented with String variable
     * @return LocalDataTime variable, correct with formatter, with date and time from string
     */
    @TypeConverter
    public static LocalDateTime stringToDate(String str) {
        return LocalDateTime.parse(str, formatter);
    }
}