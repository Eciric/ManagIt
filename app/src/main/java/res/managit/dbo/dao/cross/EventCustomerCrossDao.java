package res.managit.dbo.dao.cross;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;


import res.managit.dbo.relations.manytomany.cross.EventCustomerCross;

@Dao
public interface EventCustomerCrossDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEventCustomerCross(EventCustomerCross... eventCustomerCross);

    //usuwa kategorie
    @Delete
    void deleteEventCustomerCross(EventCustomerCross... eventCustomerCross);

    //modyfikuje kategorie
    @Update
    void updateEventCustomerCross(EventCustomerCross eventCustomerCross);
}
