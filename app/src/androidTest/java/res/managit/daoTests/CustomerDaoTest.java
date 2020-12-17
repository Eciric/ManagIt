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
import res.managit.dbo.entity.Customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static res.managit.dbo.DatabaseFunctions.createDatabase;

@RunWith(AndroidJUnit4.class)
public class CustomerDaoTest {
    Customer customer1 = new Customer("Jan",1);
    Customer customer2 = new Customer("Maciek",2);
    Customer customer3 = new Customer("Bartek",3);
    Customer customer5 = new Customer("Test",10);
    List<Customer> testList = new ArrayList<>(Arrays.asList(customer1, customer2, customer3));


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
        db.customerDao().deleteAll();

        db.customerDao().insertCustomer(customer1, customer2, customer3);
        assertEquals(db.customerDao().getAll().size(),testList.size());

    }
    @Test
    public void insertShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.customerDao().deleteAll();

        db.customerDao().insertCustomer(customer1, customer2);
        assertNotEquals(db.customerDao().getAll(),testList);
    }

    @Test
    public void updateShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.customerDao().deleteAll();

        db.customerDao().insertCustomer(customer2);
        List<Customer> customer = db.customerDao().getCustomerByName("Maciek");
        customer.get(0).name = "Test1";

        db.customerDao().updateCustomer(customer.get(0));
        assertEquals(db.customerDao().getCustomerByName("Test1").get(0).name,"Test1");
    }

    @Test
    public void updateShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.customerDao().deleteAll();

        db.customerDao().insertCustomer(customer1);
        db.customerDao().updateCustomer(customer1);

        assertNotEquals(db.customerDao().getCustomerByName("Jan").get(0).name,"Test1");
    }

    @Test
    public void deleteShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.customerDao().deleteAll();

        db.customerDao().insertCustomer(customer1, customer2, customer3,customer5);
        Customer cat = db.customerDao().getCustomerByName("Test").get(0);
        db.customerDao().deleteCustomer(cat);
        assertEquals(db.customerDao().getAll().size(),testList.size());
    }

    @Test
    public void deleteShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.customerDao().deleteAll();

        db.customerDao().insertCustomer(customer1, customer2, customer3,customer5);
        db.customerDao().deleteAll();
        assertNotEquals(db.customerDao().getAll().size(),testList.size());
    }

}
