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

import res.managit.dbo.entity.Product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static res.managit.dbo.DatabaseFunctions.createDatabase;

@RunWith(AndroidJUnit4.class)
public class ProductDaoTest {
    Product product1 = new Product("Dysza spryskiwacza",5,1);
    Product product2 = new Product("Narzedzia",100,1);
    Product product3 = new Product("Wycieraczki",500,1);
    Product product4 = new Product("Filtr oleju",60,2);
    Product product5 = new Product("Filtr powietrza",44,2);
    Product product6 = new Product("Osuszacz",10,3);
    Product product7 = new Product("Parownik",18,3);
    Product product8 = new Product("Zderzak",20,4);
    Product product9 = new Product("Blotnik",36,4);
    Product product10 = new Product("Listwy ozdobne",15,4);
    Product product11 = new Product("Test",1,1);
    List<Product> testList = new ArrayList<>(Arrays.asList(product1, product2, product3, product4,product5, product6, product7, product8, product9, product10));


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
        db.productDao().deleteAll();

        db.productDao().insertProduct(product1, product2, product3, product4,product5, product6, product7, product8, product9, product10);
        assertEquals(db.productDao().getAll().size(),testList.size());

    }
    @Test
    public void insertShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.productDao().deleteAll();

        db.productDao().insertProduct(product1, product2, product4);
        assertNotEquals(db.productDao().getAll(),testList);
    }

    @Test
    public void updateShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.productDao().deleteAll();

        db.productDao().insertProduct(product1);
        product1 = db.productDao().getProductByName("Dysza spryskiwacza");
        product1.name = "Test";

        db.productDao().updateProduct(product1);
        assertEquals(db.productDao().getProductByName("Test").name,"Test");
    }

    @Test
    public void updateShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.productDao().deleteAll();

        db.productDao().insertProduct(product1);
        db.productDao().updateProduct(product1);
        assertNotEquals(db.productDao().getProductByName("Dysza spryskiwacza").name,"Test");
    }

    @Test
    public void deleteShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.productDao().deleteAll();

        db.productDao().insertProduct(product1, product2, product3, product4,product5, product6, product7, product8, product9, product10,product11);
        Product product = db.productDao().getProductByName("Test");
        db.productDao().deleteProduct(product);
        assertEquals(db.productDao().getAll().size(),testList.size());
    }

    @Test
    public void deleteShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.productDao().deleteAll();

        db.productDao().insertProduct(product1, product2, product3, product4,product5, product6, product7, product8, product9, product10, product11);
        db.productDao().deleteAll();
        assertNotEquals(db.productDao().getAll().size(),testList.size());
    }

}
