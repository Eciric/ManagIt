package res.managit.dbo.dao.cross;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import res.managit.dbo.relations.manytomany.cross.EventProductCross;


@Dao
public interface EventProductCrossDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEventProductCross(EventProductCross... eventProductCross);

    //usuwa kategorie
    @Delete
    void deleteEventProductCross(EventProductCross... eventProductCross);

    //modyfikuje kategorie
    @Update
    void updateEventProductCross(EventProductCross eventProductCross);
}
