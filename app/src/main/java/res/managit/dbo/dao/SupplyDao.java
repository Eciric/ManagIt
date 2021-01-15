package res.managit.dbo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import res.managit.dbo.entity.Supply;
import res.managit.dbo.relations.manytomany.EventSupply;
import res.managit.dbo.relations.manytomany.SupplyEvent;
import res.managit.dbo.relations.onetoone.ContactSupply;

/**
 * Class which is a Data Access Object for Supply table in Room database
 */
@Dao
public interface SupplyDao {

    /**
     * Function which represents specific SQL query
     *
     * @return list of all entries in Supply table
     */
    @Query("SELECT * FROM Supply")
    List<Supply> getAll();

    /**
     * Function which represents specific SQL query
     *
     * @param name name of the searched supplier
     * @return Supply object with specific name
     */
    @Query("SELECT * FROM Supply WHERE supply_name LIKE :name")
    Supply getSupplyByName(String name);

    /**
     * Function which represents specific SQL query
     *
     * @param id identity number of searched supplier
     * @return Supply object with specific id
     */
    @Query("SELECT * FROM Supply WHERE supplyId LIKE :id")
    Supply getSupplyById(long id);

    /**
     * Function which represents specific SQL query
     *
     * @return List of ContactSupply objects - class connecting the supplier with his contact
     */
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Contact Inner Join Supply where contact_Id = contactId")
    List<ContactSupply> getContactAndSupply();

    /**
     * Function which represents specific SQL query
     *
     * @param name name of the searched supplier
     * @return ContactSupply object - class connecting the supplier, who have a specific name, with his contact
     */
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Contact Inner Join Supply where contact_Id = contactId and supply_name Like :name")
    ContactSupply getContactAndSupplyByName(String name);

    /**
     * Function which represents specific SQL query
     *
     * @param name name of the searched supplier
     * @return List of SupplyEvent objects - class connecting the supplier, who have a specific name, with all events in which he is included
     */
    @Transaction
    @Query("Select * from Supply where supply_name like :name")
    List<SupplyEvent> getSupplyWithEvents(String name);

    /**
     * Function which insert new entry to Supply table
     *
     * @param supplies supply objects to be inserted into Supply table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSupply(Supply... supplies);

    /**
     * Function which delete specific entry in Supply table
     *
     * @param supplies supply object to be deleted from Supply table
     */
    @Delete
    void deleteSupply(Supply... supplies);

    /**
     * Function which delete all entries in Supply table
     */
    @Query("DELETE FROM supply")
    void deleteAll();

    /**
     * Function which sets generating Id back to 1
     */
    @Query("DELETE FROM sqlite_sequence WHERE name = 'Supply'")
    void deleteNumber();

    /**
     * Function which update specific entry in Supply table
     *
     * @param supply supply object to be updated in Supply table
     */
    @Update
    void updateSupply(Supply supply);
}
