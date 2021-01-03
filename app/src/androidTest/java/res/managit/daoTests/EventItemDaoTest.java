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
import res.managit.dbo.entity.EventItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static res.managit.dbo.DatabaseFunctions.createDatabase;

@RunWith(AndroidJUnit4.class)
public class EventItemDaoTest {
    EventItem eventItem1 = new EventItem(30,2,3);
    EventItem eventItem2 = new EventItem(310,3,2);
    EventItem eventItem3 = new EventItem(330,5,1);
    EventItem eventItem5 = new EventItem(45,1,1);

    List<EventItem> testList = new ArrayList<>(Arrays.asList(eventItem1, eventItem2, eventItem3));



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
        db.eventItemDao().deleteAll();

        db.eventItemDao().insertEventItem(eventItem1, eventItem2, eventItem3);
        assertEquals(db.eventItemDao().getAll().size(),testList.size());
    }

    @Test
    public void insertShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.eventItemDao().deleteAll();

        db.eventItemDao().insertEventItem(eventItem1, eventItem2);
        assertNotEquals(db.eventItemDao().getAll(),testList);
    }

    @Test
    public void updateShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.eventItemDao().deleteAll();

        db.eventItemDao().insertEventItem(eventItem2);
        List<EventItem> eventItems = db.eventItemDao().getEventItemByAmount(310);
        eventItems.get(0).amount = 20;

        db.eventItemDao().updateEventItem(eventItems.get(0));
        assertEquals(db.eventItemDao().getEventItemByAmount(20).get(0).amount,20);
    }

    @Test
    public void deleteShouldBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.eventItemDao().deleteAll();

        db.eventItemDao().insertEventItem(eventItem1, eventItem2, eventItem3,eventItem5);
        List<EventItem> eventItems = db.eventItemDao().getEventItemByAmount(45);
        db.eventItemDao().deleteEventItem(eventItems.get(0));
        assertEquals(db.eventItemDao().getAll().size(),testList.size());
    }

    @Test
    public void deleteShouldNotBeEqual(){
        Context context = ApplicationProvider.getApplicationContext();
        WarehouseDb db = createDatabase(context, "TestB");
        db.eventItemDao().deleteAll();

        db.eventItemDao().insertEventItem(eventItem1, eventItem2, eventItem3,eventItem5);
        db.eventItemDao().deleteAll();
        assertNotEquals(db.eventItemDao().getAll().size(),testList.size());
    }
}
