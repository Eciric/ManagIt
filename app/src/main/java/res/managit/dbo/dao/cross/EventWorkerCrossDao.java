package res.managit.dbo.dao.cross;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import res.managit.dbo.relations.manytomany.cross.EventWorkerCross;

/**
 * Class which is a Data Access Object for EventWorkerCross table in Room database
 * This class is needed in Room library to create many-to-many relationship between Event and Worker table
 */
@Dao
public interface EventWorkerCrossDao {
    /**
     * Function which insert new entry to EventWorkerCross table
     *
     * @param eventWorkerCross eventWorkerCross objects to be inserted into EventWorkerCross table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEventWorkerCross(EventWorkerCross... eventWorkerCross);

    /**
     * Function which delete specific entry in EventWorkerCross table
     *
     * @param eventWorkerCross eventWorkerCross object to be deleted from EventWorkerCross table
     */
    @Delete
    void deleteEventWorkerCross(EventWorkerCross... eventWorkerCross);

    /**
     * Function which update specific entry in EventWorkerCross table
     *
     * @param eventWorkerCross eventWorkerCross object to be updated in EventWorkerCross table
     */
    @Update
    void updateEventWorkerCross(EventWorkerCross eventWorkerCross);
}
