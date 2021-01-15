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

/**
 * Class which is a Data Access Object for Customer table in Room database
 */
@Dao
public interface CustomerDao {

    /**
     * Function which represents specific SQL query
     *
     * @return list of all entries in Customer table
     */
    @Query("SELECT * FROM Customer")
    List<Customer> getAll();

    /**
     * Function which represents specific SQL query
     *
     * @param name name of the searched customer
     * @return list of Customer objects with specific name
     */
    @Query("SELECT * FROM Customer WHERE customer_name LIKE :name")
    List<Customer> getCustomerByName(String name);

    /**
     * Function which represents specific SQL query
     *
     * @param id identity number of searched customer
     * @return Customer object with specific id
     */
    @Query("SELECT * FROM Customer WHERE customerId LIKE :id")
    Customer getCustomerById(long id);

    /**
     * Function which represents specific SQL query
     *
     * @return List of ContactCustomer objects - class connecting the customer with his contact
     */
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Contact Inner Join Customer where contact_Id = contactId")
    List<ContactCustomer> getCustomerAndContact();

    /**
     * Function which represents specific SQL query
     *
     * @param name name of the searched customer
     * @return List of ContactCustomer objects - class connecting the customer, who have a specific name, with his contact
     */
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Contact Inner Join Customer where contact_Id = contactId and customer_name Like :name")
    List<ContactCustomer> getCustomerAndContactByName(String name);

    /**
     * Function which represents specific SQL query
     *
     * @param name name of the searched customer
     * @return List of CustomerEvent objects - class connecting the customer, who have a specific name, with all events in which he is included
     */
    @Transaction
    @Query("Select * from Customer where customer_name like :name")
    List<CustomerEvent> getCustomerAndEvents(String name);

    /**
     * Function which insert new entry to Customer table
     *
     * @param customers customer objects to be inserted into Customer table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCustomer(Customer... customers);

    /**
     * Function which delete specific entry in Customer table
     *
     * @param customers customer object to be deleted from Customer table
     */
    @Delete
    void deleteCustomer(Customer... customers);

    /**
     * Function which delete all entries in Customer table
     */
    @Query("DELETE FROM customer")
    void deleteAll();

    /**
     * Function which sets generating Id back to 1
     */
    @Query("DELETE FROM sqlite_sequence WHERE name = 'Customer'")
    void deleteNumber();

    /**
     * Function which update specific entry in Customer table
     *
     * @param customer customer object to be updated in Customer table
     */
    @Update
    void updateCustomer(Customer customer);

}
