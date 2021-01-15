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

import res.managit.dbo.entity.Worker;
import res.managit.dbo.relations.manytomany.EventWorker;
import res.managit.dbo.relations.manytomany.WorkerEvent;
import res.managit.dbo.relations.onetoone.ContactWorker;

/**
 * Class which is a Data Access Object for Worker table in Room database
 */
@Dao
public interface WorkerDao {
    /**
     * Function which represents specific SQL query
     *
     * @return list of all entries in Worker table
     */
    @Query("SELECT * FROM Worker")
    List<Worker> getAll();

    /**
     * Function which represents specific SQL query
     *
     * @param name name of the searched worker
     * @return Worker objects with specific name
     */
    @Query("SELECT * FROM Worker WHERE worker_name LIKE :name")
    Worker getWorkerByName(String name);

    /**
     * Function which represents specific SQL query
     *
     * @param id identity number of searched worker
     * @return Worker object with specific id
     */
    @Query("SELECT * FROM Worker WHERE workerId LIKE :id")
    Worker getWorkerById(long id);

    /**
     * Function which represents specific SQL query
     *
     * @return List of ContactWorker objects - class connecting the worker with his contact
     */
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Contact Inner Join Worker where contact_Id = contactId")
    List<ContactWorker> getContactsAndWorker();

    /**
     * Function which represents specific SQL query
     *
     * @param name name of the searched worker
     * @return ContactWorker object - class connecting the worker, who have a specific name, with his contact
     */
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Contact Inner Join Worker where contact_Id = contactId and worker_name Like :name")
    ContactWorker getContactAndWorkerByName(String name);

    /**
     * Function which represents specific SQL query
     *
     * @param name name of the searched worker
     * @return List of WorkerEvent objects - class connecting the worker, who have a specific name, with all events in which he is included
     */
    @Transaction
    @Query("Select * from Worker where worker_name like :name")
    List<WorkerEvent> getWorkerWithAllEvents(String name);

    /**
     * Function which insert new entry to Worker table
     *
     * @param workers worker objects to be inserted into Worker table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWorker(Worker... workers);

    /**
     * Function which delete specific entry in Worker table
     *
     * @param workers worker object to be deleted from Worker table
     */
    @Delete
    void deleteWorker(Worker... workers);

    /**
     * Function which delete all entries in Worker table
     */
    @Query("DELETE FROM worker")
    void deleteAll();

    /**
     * Function which sets generating Id back to 1
     */
    @Query("DELETE FROM sqlite_sequence WHERE name = 'Worker'")
    void deleteNumber();

    /**
     * Function which update specific entry in Worker table
     *
     * @param worker customer object to be updated in Customer table
     */
    @Update
    void updateWorker(Worker worker);
}
