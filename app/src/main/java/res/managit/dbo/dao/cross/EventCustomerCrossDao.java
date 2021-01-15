package res.managit.dbo.dao.cross;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;


import res.managit.dbo.relations.manytomany.cross.EventCustomerCross;

/**
 * Class which is a Data Access Object for EventCustomerCross table in Room database
 * This class is needed in Room library to create many-to-many relationship between Event and Customer table
 */
@Dao
public interface EventCustomerCrossDao {

    /**
     * Function which insert new entry to EventCustomerCross table
     *
     * @param eventCustomerCross eventCustomerCross objects to be inserted into EventCustomerCross table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEventCustomerCross(EventCustomerCross... eventCustomerCross);

    /**
     * Function which delete specific entry in EventCustomerCross table
     *
     * @param eventCustomerCross eventCustomerCross object to be deleted from EventCustomerCross table
     */
    @Delete
    void deleteEventCustomerCross(EventCustomerCross... eventCustomerCross);

    /**
     * Function which update specific entry in EventCustomerCross table
     *
     * @param eventCustomerCross eventCustomerCross object to be updated in EventCustomerCross table
     */
    @Update
    void updateEventCustomerCross(EventCustomerCross eventCustomerCross);
}
