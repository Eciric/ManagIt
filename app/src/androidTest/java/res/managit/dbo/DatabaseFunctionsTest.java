package res.managit.dbo;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.apache.commons.lang3.ObjectUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static res.managit.dbo.DatabaseFunctions.convertStrLong;
import static res.managit.dbo.DatabaseFunctions.createDatabase;
import static res.managit.dbo.DatabaseFunctions.importDatabase;
import static res.managit.dbo.DatabaseFunctions.reloadDatabaseNames;
import static res.managit.dbo.DatabaseFunctions.reloadDatabases;
import static res.managit.dbo.DatabaseFunctions.saveDatabaseNames;
import static res.managit.dbo.DatabaseFunctions.exportDatabase;

@RunWith(AndroidJUnit4.class)
public class DatabaseFunctionsTest {

    @Before
    public void setVariables(){
        PublicDatabaseAcces publicDatabaseAcces = new PublicDatabaseAcces();
        PublicDatabaseAcces.databaseList = new ArrayList<WarehouseDb>();
        PublicDatabaseAcces.databaseNameList = new ArrayList<String>();

    }

    @Test(expected = IllegalArgumentException.class)
    public void createDatabaseShouldThrowIllegalArgumentException(){
        Context context = null;
        WarehouseDb db1 = createDatabase(context, "db1");
        List<String> testList = new ArrayList<>(Arrays.asList("db1"));
    }

    @Test
    public void createDatabaseShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db1 = createDatabase(context, "db1");
        List<String> testList = new ArrayList<>(Arrays.asList("db1"));
        assertEquals(PublicDatabaseAcces.databaseNameList,testList);
    }

    @Test
    public void createDatabaseShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db1 = createDatabase(context, "db1");
        List<String> testList = new ArrayList<>(Arrays.asList("db2"));
        assertNotEquals(PublicDatabaseAcces.databaseNameList,testList);
    }

    @Test
    public void createDatabaseShouldNotBeEqual2(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db1 = createDatabase(context, "db1");
        WarehouseDb db2 = createDatabase(context, "db1");
        List<String> testList = new ArrayList<>(Arrays.asList("db1","db1"));
        assertNotEquals(PublicDatabaseAcces.databaseNameList,testList);
    }

    @Test
    public void createDatabaseShouldBeEqualDbLists(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db1 = createDatabase(context, "db1");
        WarehouseDb db2 = createDatabase(context, "db2");
        List<WarehouseDb> testList = new ArrayList<>(Arrays.asList(db1,db2));
        assertEquals(PublicDatabaseAcces.getDatabaseList(),testList);
    }

    @Test
    public void createDatabaseShouldNotBeEqualDbLists(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db1 = createDatabase(context, "db1");
        WarehouseDb db2 = createDatabase(context, "db2");
        List<WarehouseDb> testList = new ArrayList<>(Arrays.asList(db1));
        assertNotEquals(PublicDatabaseAcces.getDatabaseList(),testList);
    }

    @Test
    public void reloadDatabasesShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        List<String> testNameList = new ArrayList<>(Arrays.asList("db1","db2"));
        PublicDatabaseAcces.databaseNameList = testNameList;
        reloadDatabases(context);
        assertEquals(PublicDatabaseAcces.getDatabaseList().size(),testNameList.size());
    }

    @Test
    public void reloadDatabasesShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        List<String> testNameList = new ArrayList<>(Arrays.asList("db1","db2"));
        reloadDatabases(context);
        assertNotEquals(PublicDatabaseAcces.getDatabaseList().size(),testNameList.size());
    }

    @Test(expected = NullPointerException.class)
    public void reloadDatabaseNamesShouldThrowNullException(){
        Context context = ApplicationProvider.getApplicationContext();
        reloadDatabaseNames(context,null);
    }

    @Test(expected = NullPointerException.class)
    public void saveDatabaseNamesShouldThrowException(){
        Context context = ApplicationProvider.getApplicationContext();
        saveDatabaseNames(context,null);
    }

    @Test
    public void saveAndReloadShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        List<String> testNameList = new ArrayList<>(Arrays.asList("db1","db2"));
        WarehouseDb db1 = createDatabase(context, "db1");
        WarehouseDb db2 = createDatabase(context, "db2");
        List<WarehouseDb> testList = new ArrayList<>(Arrays.asList(db1,db2));
        saveDatabaseNames(context,"test.csv");
        PublicDatabaseAcces.databaseList = new ArrayList<WarehouseDb>();
        PublicDatabaseAcces.databaseNameList = new ArrayList<String>();
        reloadDatabaseNames(context,"test.csv");

        assertEquals(PublicDatabaseAcces.databaseNameList,testNameList);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void exportDatabaseShouldThrowIndexException(){
        exportDatabase(null);
    }


    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void importDatabaseShouldThrowIndexException() {
        importDatabase(null,null);
    }

    @Test(expected = NullPointerException.class)
    public void convertStrLongShouldThrowNullException() {
        String [] test = null;
        convertStrLong(test);
    }


}