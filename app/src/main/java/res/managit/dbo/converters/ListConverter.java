package res.managit.dbo.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListConverter {
    @TypeConverter
    public static List<Long> storedStringToLong(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Long>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String LongToStoredString(List<Long> myIds) {
        Gson gson = new Gson();
        return gson.toJson(myIds);
    }
}
