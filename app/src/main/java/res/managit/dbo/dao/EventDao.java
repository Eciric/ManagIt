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

/**
 * Class which is a Data Access Object for Event table in Room database
 */
@Dao
public interface EventDao {
    /**
     * Function which represents specific SQL query
     *
     * @return list of all entries in Event table
     */
    @Query("Select * from Event")
    List<Event> getAll();


    /**
     * Function which represents specific SQL query
     *
     * @param bool variable which is used to check that event is or not executed
     * @return List of event objects which have date later than now and specific set isExecuted variable
     */
    @Query("Select * from Event where isExecuted like :bool and date <= (SELECT strftime('%H:%M    %d-%m-%Y', datetime('now')))")
    List<Event> getEventByDateAndExecution(boolean bool);

    /**
     * Function which represents specific SQL query
     *
     * @param act name of action
     * @return list of event objects which have a specific action
     */
    @Query("SELECT * FROM Event WHERE `action` LIKE :act")
    List<Event> getEventByAction(String act);

    /**
     * Function which represents specific SQL query
     *
     * @return long number which is the id of last inserted event
     */
    @Query("Select MAX(eventId) from Event")
    long getMaxEventId();

    /**
     * Function which represents specific SQL query
     *
     * @return List of EventWorker objects - class connecting event with all his workers
     */
    @Transaction
    @Query("Select * from Event")
    List<EventWorker> getEventWithAllWorkers();


    /**
     * Function which represents specific SQL query
     *
     * @return List of EventSupply objects - class connecting event with all his suppliers
     */
    @Transaction
    @Query("Select * from Event")
    List<EventSupply> getEventWithAllSuppliers();

    /**
     * Function which represents specific SQL query
     *
     * @return List of EventCustomer objects - class connecting event with all his customers
     */
    @Transaction
    @Query("Select * from Event")
    List<EventCustomer> getEventWithAllCustomers();

    /**
     * Function which insert new entry to Event table
     *
     * @param events event objects to be inserted into Event table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEvent(Event... events);

    /**
     * Function which delete specific entry in Event table
     *
     * @param events customer object to be deleted from Event table
     */
    @Delete
    void deleteEvent(Event... events);

    /**
     * Function which delete all entries in Event table
     */
    @Query("DELETE FROM event")
    void deleteAll();

    /**
     * Function which sets generating Id back to 1
     */
    @Query("DELETE FROM sqlite_sequence WHERE name = 'Event'")
    void deleteNumber();

    /**
     * Function which update specific entry in Event table
     *
     * @param event event object to be updated in Event table
     */
    @Update
    void updateEvent(Event event);
}
