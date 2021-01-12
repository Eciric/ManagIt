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

@Dao
public interface ContactDao {

    //zwraca wszystkie kontakty
    @Query("SELECT * FROM contact")
    public List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE phoneNumber LIKE :number")
    public Contact getByPhoneNumber(String number);

    @Query("SELECT * FROM contact WHERE contactId LIKE :id")
    public Contact getById(long id);

    //wstawia nowy kontakt
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertContact(Contact... contacts);

    //usuwa kontakt
    @Delete
    void deleteContact(Contact... contacts);

    @Query("DELETE FROM Contact")
    void deleteAll();

    @Query("DELETE FROM sqlite_sequence WHERE name = 'Contact'")
    void deleteNumber();

    //modyfikuje kontakt
    @Update
    void updateContact(Contact contact);
}
