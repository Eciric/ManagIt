package res.managit.dbo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import res.managit.dbo.entity.EventItem;
import res.managit.dbo.relations.onetoone.EventItemProduct;

/**
 * Class which is a Data Access Object for EventItem table in Room database
 */
@Dao
public interface EventItemDao {

    /**
     * Function which represents specific SQL query
     *
     * @return list of all entries in EventItem table
     */
    @Query("Select * from EventItem")
    List<EventItem> getAll();

    /**
     * Function which represents specific SQL query
     *
     * @param id identity number of searched EventItem
     * @return EventItem object with specific id
     */
    @Query("Select * from EventItem where eventItemId like :id")
    EventItem getEventItemById(Long id);

    /**
     * Function which represents specific SQL query
     *
     * @param id identity of assigned event number of searched EventItem
     * @return list of EventItem objects with specific event_Id
     */
    @Query("Select * from EventItem where event_Id like :id")
    List<EventItem> getEventItemByEventId(Long id);

    /**
     * Function which represents specific SQL query
     *
     * @param eventId   identity of assigned event number of searched EventItem
     * @param productId identity of assigned product number of searched EventItem
     * @return EventItem object which have a specific eventId and productId
     */
    @Transaction
    @Query("Select * from EventItem where event_Id like :eventId and product_Id like :productId")
    EventItemProduct getEventItemByEventIdAndProductId(long eventId, long productId);

    /**
     * Function which represents specific SQL query
     *
     * @param amount amount of the searched EventItem
     * @return List of EventItem objects which have a specific amount
     */
    @Query("Select * from EventItem where amount like :amount")
    List<EventItem> getEventItemByAmount(int amount);

    /**
     * Function which insert new entry to EventItem table
     *
     * @param eventItems eventItem objects to be inserted into EventItem table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEventItem(EventItem... eventItems);

    /**
     * Function which delete specific entry in EventItem table
     *
     * @param eventItems eventItem object to be deleted from EventItem table
     */
    @Delete
    void deleteEventItem(EventItem... eventItems);

    /**
     * Function which delete all entries in EventItem table
     */
    @Query("Delete from EventItem")
    void deleteAll();

    /**
     * Function which sets generating Id back to 1
     */
    @Query("DELETE FROM sqlite_sequence WHERE name = 'EventItem'")
    void deleteNumber();

    /**
     * Function which update specific entry in EventItem table
     *
     * @param eventItem eventItem object to be updated in EventItem table
     */
    @Update
    void updateEventItem(EventItem eventItem);


}
