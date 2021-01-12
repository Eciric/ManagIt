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

@Dao
public interface SupplyDao {

    //zwraca wszystkich dostawcow
    @Query("SELECT * FROM Supply")
    public List<Supply> getAll();

    //zwraca wszystkich dostawcow o danej nazwie
    @Query("SELECT * FROM Supply WHERE supply_name LIKE :name")
    public Supply getSupplyByName(String name);

    //zwraca dostawce o danym id
    @Query("SELECT * FROM Supply WHERE supplyId LIKE :id")
    public Supply getSupplyById(long id);

    //zwraca wszystkich dostawcow i kontakty dla kazdego z nich
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Contact Inner Join Supply where contact_Id = contactId")
    public List<ContactSupply> getContactAndSupply();

    //zwraca dostawce i kontakt dla podanej nazwy
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Contact Inner Join Supply where contact_Id = contactId and supply_name Like :name")
    public ContactSupply getContactAndSupplyByName(String name);

    //zwraca dostawce o podanej nazwie i wszystkie eventy ktore sa z nim powiazane
    @Transaction
    @Query("Select * from Supply where supply_name like :name")
    public List<SupplyEvent> getSuppllyWithEvents(String name);

    //dodawanie nowego dostawcy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSupply(Supply... supplies);

    //usuwanie dostawcy
    @Delete
    void deleteSupply(Supply... supplies);

    @Query("DELETE FROM supply")
    void deleteAll();

    @Query("DELETE FROM sqlite_sequence WHERE name = 'Supply'")
    void deleteNumber();

    //modyfikowanie dostawcy
    @Update
    void updateSupply(Supply supply);
}
