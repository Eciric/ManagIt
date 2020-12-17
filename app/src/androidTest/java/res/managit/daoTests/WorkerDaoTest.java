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

import res.managit.dbo.entity.Worker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static res.managit.dbo.DatabaseFunctions.createDatabase;

@RunWith(AndroidJUnit4.class)
public class WorkerDaoTest {
    Worker worker1 = new Worker("Marek","Kowalski","manager",4);
    Worker worker2 = new Worker("Arek","Nowak","forklift",2);
    Worker worker3 = new Worker("Karol","Zulczyk","forklift",1);
    Worker worker5 = new Worker("Test","T","T",2);
    List<Worker> testList = new ArrayList<>(Arrays.asList(worker1, worker2, worker3));


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
        db.workerDao().deleteAll();

        db.workerDao().insertWorker(worker1, worker2, worker3);
        assertEquals(db.workerDao().getAll().size(),testList.size());

    }
    @Test
    public void insertShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.workerDao().deleteAll();

        db.workerDao().insertWorker(worker1, worker2);
        assertNotEquals(db.workerDao().getAll(),testList);
    }

    @Test
    public void updateShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.workerDao().deleteAll();

        db.workerDao().insertWorker(worker1);
        worker1 = db.workerDao().getWorkerByName("Marek");
        worker1.name = "Test1";

        db.workerDao().updateWorker(worker1);
        assertEquals(db.workerDao().getWorkerByName("Test1").name,"Test1");
    }

    @Test
    public void updateShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.workerDao().deleteAll();

        db.workerDao().insertWorker(worker1);
        db.workerDao().updateWorker(worker1);
        assertNotEquals(db.workerDao().getWorkerByName("Marek"),"Test");
    }

    @Test
    public void deleteShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.workerDao().deleteAll();

        db.workerDao().insertWorker(worker1, worker2, worker3,worker5);
        Worker worker = db.workerDao().getWorkerByName("Test");
        db.workerDao().deleteWorker(worker);
        assertEquals(db.workerDao().getAll().size(),testList.size());
    }

    @Test
    public void deleteShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.workerDao().deleteAll();

        db.workerDao().insertWorker(worker1, worker2, worker3,worker5);
        db.workerDao().deleteAll();
        assertNotEquals(db.workerDao().getAll().size(),testList.size());
    }

}
