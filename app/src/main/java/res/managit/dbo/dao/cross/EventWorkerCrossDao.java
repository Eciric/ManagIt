package res.managit.dbo.dao.cross;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import res.managit.dbo.relations.manytomany.cross.EventWorkerCross;

@Dao
public interface EventWorkerCrossDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEventWorkerCross(EventWorkerCross... eventWorkerCross);

    //usuwa kategorie
    @Delete
    void deleteEventWorkerCross(EventWorkerCross... eventWorkerCross);

    //modyfikuje kategorie
    @Update
    void updateEventWorkerCross(EventWorkerCross eventWorkerCross);
}
