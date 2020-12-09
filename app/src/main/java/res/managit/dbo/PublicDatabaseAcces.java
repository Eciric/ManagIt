package res.managit.dbo;

import android.content.Context;

import java.util.List;

public class PublicDatabaseAcces {
    public static List<WarehouseDb> databaseList;
    public static List<String> databaseNameList;

    public static List<WarehouseDb> getDatabaseList() {
        return databaseList;
    }

    public static void setDatabaseList(List<WarehouseDb> databaseList) {
        PublicDatabaseAcces.databaseList = databaseList;
    }



}
