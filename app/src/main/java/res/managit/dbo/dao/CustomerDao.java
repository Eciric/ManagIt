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

import res.managit.dbo.entity.Customer;
import res.managit.dbo.relations.manytomany.CustomerEvent;
import res.managit.dbo.relations.manytomany.EventCustomer;
import res.managit.dbo.relations.onetoone.ContactCustomer;

@Dao
public interface CustomerDao {

    //zwraca wszystich klientow
    @Query("SELECT * FROM Customer")
    public List<Customer> getAll();

    //zwraca wszystkich klientow o danej nazwie
    @Query("SELECT * FROM Customer WHERE customer_name LIKE :name")
    public List<Customer> getCustomerByName(String name);

    //zwraca klienta o danym id
    @Query("SELECT * FROM Customer WHERE customerId LIKE :id")
    public Customer getCustomerById(long id);

    //zwraca wszystkich klientow i kontatky do kazdego z nich
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Contact Inner Join Customer where contact_Id = contactId")
    public List<ContactCustomer> getCustomerAndContact();

    //zwraca klienta o podanej nazwie i jego kontakt
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Contact Inner Join Customer where contact_Id = contactId and customer_name Like :name")
    public List<ContactCustomer> getCustomerAndContactByName(String name);

    //zwraca klienta o podanej nazwie i wszystkie eventy ktore sa z nim powiazane
    @Transaction
    @Query("Select * from Customer where customer_name like :name")
    public List<CustomerEvent> getCustomerAndEvents(String name);

    //wstawia nowego klienta
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCustomer(Customer... customers);

    //usuwa klienta
    @Delete
    void deleteCustomer(Customer... customers);

    @Query("DELETE FROM customer")
    void deleteAll();

    //modyfikuje klienta
    @Update
    void updateCustomer(Customer customer);

}
