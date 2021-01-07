package res.managit.daoTests;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Event;
import res.managit.dbo.relations.TypeAction;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static res.managit.dbo.DatabaseFunctions.createDatabase;

@RunWith(AndroidJUnit4.class)
public class EventDaoTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    Event event1 = new Event(LocalDateTime.parse("20-12-2020 10:00",formatter),"loading",new ArrayList<Long>(Arrays.asList(2l,3l)), new ArrayList<Long>(Arrays.asList(2l,3l)),new ArrayList<Long>(Arrays.asList(1l,2l)), new ArrayList<Long>(Arrays.asList(1l)),new ArrayList<Long>(Arrays.asList(1l,5l,8l)), false);
    Event event2 = new Event(LocalDateTime.parse("05-01-2021 15:00",formatter),"unloading",new ArrayList<Long>(Arrays.asList(1l,3l)), new ArrayList<Long>(Arrays.asList(2l,3l)),new ArrayList<Long>(Arrays.asList(2l)), new ArrayList<Long>(Arrays.asList()),new ArrayList<Long>(Arrays.asList(6l,9l,10l)), false);
    Event event3 = new Event(LocalDateTime.parse("15-03-2021 11:30",formatter),"loading",new ArrayList<Long>(Arrays.asList(1l,2l)), new ArrayList<Long>(Arrays.asList(3l)),new ArrayList<Long>(Arrays.asList(1l)), new ArrayList<Long>(Arrays.asList()),new ArrayList<Long>(Arrays.asList(1l,2l,3l,4l,6l,8l,9l,10l)), false);
    Event event5 = new Event(LocalDateTime.parse("01-01-2001 10:00",formatter),"T",new ArrayList<Long>(Arrays.asList(2l)), new ArrayList<Long>(Arrays.asList(1l)),new ArrayList<Long>(Arrays.asList(1l)), new ArrayList<Long>(Arrays.asList(1l)),new ArrayList<Long>(Arrays.asList(1l)),false);

    List<Event> testList = new ArrayList<>(Arrays.asList(event1, event2, event3));


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
        db.eventDao().deleteAll();

        db.eventDao().insertEvent(event1, event2, event3);
        assertEquals(db.eventDao().getAll().size(),testList.size());

    }
    @Test
    public void insertShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.eventDao().deleteAll();

        db.eventDao().insertEvent(event1, event2);
        assertNotEquals(db.eventDao().getAll(),testList);
    }

    @Test
    public void updateShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.eventDao().deleteAll();

        db.eventDao().insertEvent(event2);
        List<Event> event = db.eventDao().getEventByAction(TypeAction.UnLoading.label);
        event.get(0).action = "Test";

        db.eventDao().updateEvent(event.get(0));
        assertEquals(db.eventDao().getEventByAction("Test").get(0).action,"Test");
    }

    @Test
    public void updateShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.eventDao().deleteAll();

        db.eventDao().insertEvent(event1);
        db.eventDao().updateEvent(event1);
        assertNotEquals(db.eventDao().getEventByAction(TypeAction.Loading.label).get(0).action,"Test");
    }

    @Test
    public void deleteShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.eventDao().deleteAll();

        db.eventDao().insertEvent(event1, event2, event3,event5);
        List<Event> event = db.eventDao().getEventByAction(TypeAction.UnLoading.label);
        db.eventDao().deleteEvent(event.get(0));
        assertEquals(db.eventDao().getAll().size(),testList.size());
    }

    @Test
    public void deleteShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.eventDao().deleteAll();

        db.eventDao().insertEvent(event1, event2, event3,event5);
        db.eventDao().deleteAll();
        assertNotEquals(db.eventDao().getAll().size(),testList.size());
    }

}
