package res.managit.dbo.dao.cross;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import res.managit.dbo.relations.manytomany.cross.EventSupplyCross;

/**
 * Class which is a Data Access Object for EventSupplyCross table in Room database
 * This class is needed in Room library to create many-to-many relationship between Event and Supply table
 */
@Dao
public interface EventSupplyCrossDao {
    /**
     * Function which insert new entry to EventSupplyCross table
     *
     * @param eventSupplyCross eventSupplyCross objects to be inserted into EventSupplyCross table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEventSupplyCross(EventSupplyCross... eventSupplyCross);

    /**
     * Function which delete specific entry in EventSupplyCross table
     *
     * @param eventSupplyCross eventSupplyCross object to be deleted from EventSupplyCross table
     */
    @Delete
    void deleteEventSupplyCross(EventSupplyCross... eventSupplyCross);

    /**
     * Function which update specific entry in EventSupplyCross table
     *
     * @param eventSupplyCross eventSupplyCross object to be updated in EventSupplyCross table
     */
    @Update
    void updateEventSupplyCross(EventSupplyCross eventSupplyCross);
}
