package res.managit.dbo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;

/**
 * Class which is a Data Access Object for Contact table in Room database
 */
@Dao
public interface ContactDao {

    /**
     * Function which represents specific SQL query
     *
     * @return list of all entries in Contact table
     */
    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    /**
     * Function which represents specific SQL query
     *
     * @param number phone number of the searched contact
     * @return Contact object with specific phoneNumber
     */
    @Query("SELECT * FROM contact WHERE phoneNumber LIKE :number")
    Contact getByPhoneNumber(String number);

    /**
     * Function which represents specific SQL query
     *
     * @param id identity number of searched contact
     * @return Contact object with specific id
     */
    @Query("SELECT * FROM contact WHERE contactId LIKE :id")
    Contact getById(long id);

    /**
     * Function which insert new entry to Contact table
     *
     * @param contacts contact objects to be inserted into Contact table
     * @return table of inserted contacts id's
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertContact(Contact... contacts);

    /**
     * function which delete specific entry in Contact table
     *
     * @param contacts contact object to be deleted from Contact table
     */
    @Delete
    void deleteContact(Contact... contacts);

    /**
     * Function which delete all entries in Contact table
     */
    @Query("DELETE FROM Contact")
    void deleteAll();

    /**
     * Function which set generating Id back to 1
     */
    @Query("DELETE FROM sqlite_sequence WHERE name = 'Contact'")
    void deleteNumber();


    /**
     * Function which update specific entry in Contact table
     *
     * @param contact contact object to be updated in Contact table
     */
    @Update
    void updateContact(Contact contact);
}
