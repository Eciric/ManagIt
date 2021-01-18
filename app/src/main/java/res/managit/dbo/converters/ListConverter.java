package res.managit.dbo.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Class created to convert List to String and vice versa
 * It is needed for correct saving of List in Room database tables
 */
public class ListConverter {
    /**
     * Function converting String to List of Long
     *
     * @param data List represented by the String variable
     * @return List with Long elements
     */
    @TypeConverter
    public static List<Long> storedStringToLong(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Long>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    /**
     * Function converting List of Long to String
     *
     * @param myIds List with Long elements
     * @return String variable which represents List myIds
     */
    @TypeConverter
    public static String LongToStoredString(List<Long> myIds) {
        Gson gson = new Gson();
        return gson.toJson(myIds);
    }
}
