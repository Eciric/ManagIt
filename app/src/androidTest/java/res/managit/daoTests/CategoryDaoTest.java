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
import res.managit.dbo.entity.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static res.managit.dbo.DatabaseFunctions.createDatabase;

@RunWith(AndroidJUnit4.class)
public class CategoryDaoTest {
    Category category1 = new Category("Czyszczenie szyb");
    Category category2 = new Category("Filtry");
    Category category3 = new Category("Klimatyzacja");
    Category category4 = new Category("Nadwozie");
    Category category5 = new Category("Test");
    List<Category> testList = new ArrayList<>(Arrays.asList(category1, category2, category3, category4));


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
        db.categoryDao().deleteAll();

        db.categoryDao().insertCategory(category1, category2, category3, category4);
        assertEquals(db.categoryDao().getAll().size(),testList.size());

    }
    @Test
    public void insertShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.categoryDao().deleteAll();

        db.categoryDao().insertCategory(category1, category2, category4);
        assertNotEquals(db.categoryDao().getAll(),testList);
    }

    @Test
    public void updateShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.categoryDao().deleteAll();

        db.categoryDao().insertCategory(category1);
        category1 = db.categoryDao().getCategoryByName("Czyszczenie szyb");
        category1.name = "Test";

        db.categoryDao().updateCategory(category1);
        assertEquals(db.categoryDao().getCategoryByName("Test").name,"Test");
    }

    @Test
    public void updateShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.categoryDao().deleteAll();

        db.categoryDao().insertCategory(category1);
        db.categoryDao().updateCategory(category1);
        assertNotEquals(db.categoryDao().getCategoryByName("Test"),"Test");
    }

    @Test
    public void deleteShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.categoryDao().deleteAll();

        db.categoryDao().insertCategory(category1, category2, category3, category4,category5);
        Category cat = db.categoryDao().getCategoryByName("Test");
        db.categoryDao().deleteCategory(cat);
        assertEquals(db.categoryDao().getAll().size(),testList.size());
    }

    @Test
    public void deleteShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.categoryDao().deleteAll();

        db.categoryDao().insertCategory(category1, category2, category3, category4,category5);
        db.categoryDao().deleteAll();
        assertNotEquals(db.categoryDao().getAll().size(),testList.size());
    }

}
