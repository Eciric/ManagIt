package res.managit.dbo;

import java.util.List;

/**
 * Class created to get easy access to variables connected with Room database
 */
public class PublicDatabaseAcces {
    public static List<WarehouseDb> databaseList;
    public static List<String> databaseNameList;
    public static String currentDatabaseName;
    public static WarehouseDb currentDatabase;


    /**
     * Function to get list with with all databases on that device
     * @return list with with all databases on that device
     */
    public static List<WarehouseDb> getDatabaseList() {
        return databaseList;
    }

    /**
     * Function to set list with with all databases on that device
     * @param databaseList list with databases on that device
     */
    public static void setDatabaseList(List<WarehouseDb> databaseList) {
        PublicDatabaseAcces.databaseList = databaseList;
    }

    /**
     * Function to find Database by unique name
     * @param name name of searched database
     * @return searched database with specific name
     */
    public static WarehouseDb getDatabaseByName(String name) {
        int index = databaseNameList.indexOf(name);
        return databaseList.get(index);
    }

}
