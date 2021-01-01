package res.managit.daoTests;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;

import res.managit.dbo.entity.Supply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static res.managit.dbo.DatabaseFunctions.createDatabase;

@RunWith(AndroidJUnit4.class)
public class SupplyDaoTest {
    Supply supply1 = new Supply("iParts",6);
    Supply supply2 = new Supply("CarStore",5);
    Supply supply3 = new Supply("CarsStore",6);
    Supply supply5 = new Supply("Test",1);
    List<Supply> testList = new ArrayList<>(Arrays.asList(supply1, supply2, supply3));


    @Before
    public void setVariables(){
        PublicDatabaseAcces publicDatabaseAcces = new PublicDatabaseAcces();
        PublicDatabaseAcces.databaseList = new ArrayList<WarehouseDb>();
        PublicDatabaseAcces.databaseNameList = new ArrayList<String>();

    }


    @Test
    public void insertShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.supplyDao().deleteAll();

        db.supplyDao().insertSupply(supply1, supply2, supply3);
        assertEquals(db.supplyDao().getAll().size(),testList.size());

    }
    @Test
    public void insertShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.supplyDao().deleteAll();

        db.supplyDao().insertSupply(supply1, supply2);
        assertNotEquals(db.supplyDao().getAll(),testList);
    }

    @Test
    public void updateShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.supplyDao().deleteAll();

        db.supplyDao().insertSupply(supply1);
        supply1 = db.supplyDao().getSupplyByName("iParts");
        supply1.name = "Test";

        db.supplyDao().updateSupply(supply1);
        assertEquals(db.supplyDao().getSupplyByName("Test").name,"Test");
    }

    @Test
    public void updateShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.supplyDao().deleteAll();

        db.supplyDao().insertSupply(supply1);
        db.supplyDao().updateSupply(supply1);
        assertNotEquals(db.supplyDao().getSupplyByName("iParts"),"Test");
    }

    @Test
    public void deleteShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.supplyDao().deleteAll();

        db.supplyDao().insertSupply(supply1, supply2, supply3,supply5);
        Supply supply = db.supplyDao().getSupplyByName("Test");
        db.supplyDao().deleteSupply(supply);
        assertEquals(db.supplyDao().getAll().size(),testList.size());
    }

    @Test
    public void deleteShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.supplyDao().deleteAll();

        db.supplyDao().insertSupply(supply1, supply2, supply3);
        db.supplyDao().deleteAll();
        assertNotEquals(db.supplyDao().getAll().size(),testList.size());
    }

}
