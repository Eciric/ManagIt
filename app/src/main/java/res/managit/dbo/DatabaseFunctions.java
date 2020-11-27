package res.managit.dbo;

import android.content.Context;

import androidx.room.Room;

public abstract class DatabaseFunctions {
    public static WarehouseDb createDatabase(Context context, String name){
        WarehouseDb Db = Room.databaseBuilder(context, WarehouseDb.class, name).allowMainThreadQueries().build();
        PublicDatabaseAcces.databaseList.add(Db);
        PublicDatabaseAcces.databaseNameList.add(name);
        return Db;
    }
}
