package res.managit.dbo.dao.cross;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import res.managit.dbo.relations.manytomany.cross.EventSupplyCross;

@Dao
public interface EventSupplyCrossDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEventSupplyCross(EventSupplyCross... eventSupplyCross);

    //usuwa kategorie
    @Delete
    void deleteEventSupplyCross(EventSupplyCross... eventSupplyCross);

    //modyfikuje kategorie
    @Update
    void updateEventSupplyCross(EventSupplyCross eventSupplyCross);
}
