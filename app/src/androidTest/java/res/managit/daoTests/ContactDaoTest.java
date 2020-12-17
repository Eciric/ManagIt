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
import res.managit.dbo.entity.Contact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static res.managit.dbo.DatabaseFunctions.createDatabase;

@RunWith(AndroidJUnit4.class)
public class ContactDaoTest {
    Contact contact1 = new Contact("Kwiatowa",15,97222,"Lodz","Polska","987654321");
    Contact contact2 = new Contact("Sloneczna",22,97225,"Lodz","Polska","123456789");
    Contact contact3 = new Contact("Nowa",57,90258,"Tomaszow","Polska","589623748");
    Contact contact4 = new Contact("Piekna",95,95693,"Warszawa","Polska","598475692");
    Contact contact5 = new Contact("Zelazna",62,98586,"Kielce","Polska","548921458");
    Contact contact6 = new Contact("Zlota",15,98669,"Nowy Sacz","Polska","156987563");
    Contact contact7 = new Contact("Test",1,1,"T","T","1");

    List<Contact> testList = new ArrayList<>(Arrays.asList(contact1, contact2, contact3, contact4,contact5,contact6));


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
        db.contactDao().deleteAll();

        db.contactDao().insertContact(contact1, contact2, contact3, contact4,contact5,contact6);
        assertEquals(db.contactDao().getAll().size(),testList.size());

    }
    @Test
    public void insertShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.contactDao().deleteAll();

        db.contactDao().insertContact(contact1, contact2, contact3,contact6);
        assertNotEquals(db.contactDao().getAll().size(),testList.size());
    }

    @Test
    public void updateShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.contactDao().deleteAll();

        db.contactDao().insertContact(contact1);
        contact1 = db.contactDao().getByPhoneNumber("987654321");
        contact1.phoneNumber = "147852369";

        db.contactDao().updateContact(contact1);
        assertEquals(db.contactDao().getByPhoneNumber("147852369").phoneNumber,"147852369");
    }

    @Test
    public void updateShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.contactDao().deleteAll();

        db.contactDao().insertContact(contact1);
        db.contactDao().updateContact(contact1);
        assertNotEquals(db.contactDao().getByPhoneNumber("987654321").phoneNumber,"Test");

    }

    @Test
    public void deleteShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.contactDao().deleteAll();

        db.contactDao().insertContact(contact1, contact2, contact3, contact4,contact5,contact6,contact7);
        Contact cat = db.contactDao().getByPhoneNumber("1");
        db.contactDao().deleteContact(cat);
        assertEquals(db.contactDao().getAll().size(),testList.size());
    }

    @Test
    public void deleteShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.contactDao().deleteAll();

        db.contactDao().insertContact(contact1, contact2, contact3, contact4,contact5,contact6,contact7);
        db.contactDao().deleteAll();
        assertNotEquals(db.contactDao().getAll().size(),testList.size());
    }

}
