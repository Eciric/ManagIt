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

    //wstawia nowy kontakt
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertContact(Contact... contacts);

    //usuwa kontakt
    @Delete
    void deleteContact(Contact... contacts);

    //modyfikuje kontakt
    @Update
    void updateContact(Contact contact);
}
