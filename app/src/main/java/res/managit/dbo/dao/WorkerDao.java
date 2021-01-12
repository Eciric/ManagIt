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

@Dao
public interface WorkerDao {
    //zwraca wszystkich pracownikow
    @Query("SELECT * FROM Worker")
    public List<Worker> getAll();

    //zwraca wszystkich pracownikow o podanej naziwe
    @Query("SELECT * FROM Worker WHERE worker_name LIKE :name")
    public Worker getWorkerByName(String name);

    //zwraca wszystkich pracownikow o podanym id
    @Query("SELECT * FROM Worker WHERE workerId LIKE :id")
    public Worker getWorkerById(long id);

    //zwraca wsztstkich pracownikow i kontakt do kazdego z nich
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Contact Inner Join Worker where contact_Id = contactId")
    public List<ContactWorker> getContactsAndWorker();

    //zwraca pracownika o podanej nazwie i kontakt do niego
    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM Contact Inner Join Worker where contact_Id = contactId and worker_name Like :name")
    public ContactWorker getContactAndWorkerByName(String name);

    //zwraca pracownika o danej nazwie i wszystkich eventach w jakich bierze udział
    @Transaction
    @Query("Select * from Worker where worker_name like :name")
    public List<WorkerEvent> getWorkerWithAllEvents(String name);

    //wstawia nowego pracownika
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWorker(Worker... workers);

    //usuwa pracownika
    @Delete
    void deleteWorker(Worker... workers);

    @Query("DELETE FROM worker")
    void deleteAll();

    @Query("DELETE FROM sqlite_sequence WHERE name = 'Worker'")
    void deleteNumber();

    //modyfikuje pracownika
    @Update
    void updateWorker(Worker worker);
}
