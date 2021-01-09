package res.managit.dbo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import res.managit.dbo.entity.Event;
import res.managit.dbo.relations.manytomany.EventCustomer;
import res.managit.dbo.relations.manytomany.EventSupply;
import res.managit.dbo.relations.manytomany.EventWorker;


@Dao
public interface EventDao {
    @Query("Select * from Event")
     public List<Event> getAll();


    @Query("Select * from Event where isExecuted like :bool and date <= (SELECT strftime('%H:%M    %d-%m-%Y', datetime('now')))")
    public List<Event> getEventByDateAndExecution(boolean bool);

    @Query("SELECT * FROM Event WHERE `action` LIKE :act")
    public List<Event> getEventByAction(String act);

    @Query("Select MAX(eventId) from Event")
    public long getMaxEventId();

    //zwraca event i wszystkich pracownikow ktorzy z nim sa powiazani
    @Transaction
    @Query("Select * from Event")
    public List<EventWorker> getEventWithAllWorkers();


    //zwraca event i wszystkich dostawcow ktorzy sa z nim powiazani
    @Transaction
    @Query("Select * from Event")
    public List<EventSupply> getEventWithAllSuppliers();

    //zwraca event i wszystkich klientow ktorzy sa z nim powiazani
    @Transaction
    @Query("Select * from Event")
    public List<EventCustomer> getEventWithAllCustomers();

    //wstawia nowy event
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEvent(Event... events);

    //usuwa event
    @Delete
    void deleteEvent(Event... events);

    @Query("DELETE FROM event")
    void deleteAll();

    //modyfikuje event
    @Update
    void updateEvent(Event event);
}
