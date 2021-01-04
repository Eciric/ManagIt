package res.managit.dbo;

import java.util.List;

public class PublicDatabaseAcces {
    public static List<WarehouseDb> databaseList;
    public static List<String> databaseNameList;
    public static String currentDatabaseName;
    public static WarehouseDb currentDatabase;

    public static List<WarehouseDb> getDatabaseList() {
        return databaseList;
    }

    public static void setDatabaseList(List<WarehouseDb> databaseList) {
        PublicDatabaseAcces.databaseList = databaseList;
    }

    public static WarehouseDb getDatabaseByName(String name) {
        int index = databaseNameList.indexOf(name);
        return databaseList.get(index);
    }

}
