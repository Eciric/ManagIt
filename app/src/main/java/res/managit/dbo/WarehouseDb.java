package res.managit.dbo;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import res.managit.dbo.converters.DateConverter;
import res.managit.dbo.converters.ListConverter;
import res.managit.dbo.dao.CategoryDao;
import res.managit.dbo.dao.ContactDao;
import res.managit.dbo.dao.CustomerDao;
import res.managit.dbo.dao.EventDao;
import res.managit.dbo.dao.EventItemDao;
import res.managit.dbo.dao.ProductDao;
import res.managit.dbo.dao.SupplyDao;
import res.managit.dbo.dao.WorkerDao;
import res.managit.dbo.dao.cross.EventCustomerCrossDao;
import res.managit.dbo.dao.cross.EventSupplyCrossDao;
import res.managit.dbo.dao.cross.EventWorkerCrossDao;
import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.EventItem;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;
import res.managit.dbo.relations.manytomany.cross.EventCustomerCross;
import res.managit.dbo.relations.manytomany.cross.EventSupplyCross;
import res.managit.dbo.relations.manytomany.cross.EventWorkerCross;

/**
 * Class which represents Warehouse database.
 * In Database annotation it contains all tables which will be inserted in that database.
 * In TypeConverters annotation are included converters needed to change types of some variables to save them in Room database.
 */
@Database(entities = {Category.class, Contact.class, Customer.class, Event.class, Product.class,
        Supply.class, Worker.class, EventItem.class, EventCustomerCross.class,
        EventSupplyCross.class, EventWorkerCross.class}, version = 5, exportSchema = false)
@TypeConverters({ListConverter.class, DateConverter.class})
public abstract class WarehouseDb extends RoomDatabase {

    /**
     * @return access to Category Data Access Object
     */
    public abstract CategoryDao categoryDao();

    /**
     * @return access to Contact Data Access Object
     */
    public abstract ContactDao contactDao();

    /**
     * @return access to Customer Data Access Object
     */
    public abstract CustomerDao customerDao();

    /**
     * @return access to Event Data Access Object
     */
    public abstract EventDao eventDao();

    /**
     * @return access to Product Data Access Object
     */
    public abstract ProductDao productDao();

    /**
     * @return access to Supply Data Access Object
     */
    public abstract SupplyDao supplyDao();

    /**
     * @return access to Worker Data Access Object
     */
    public abstract WorkerDao workerDao();

    /**
     * @return access to EventItem Data Access Object
     */
    public abstract EventItemDao eventItemDao();

    /**
     * @return access to EventCustomerCross Data Access Object
     */
    public abstract EventCustomerCrossDao eventCustomerCrossDao();

    /**
     * @return access to EventWorkerCross Data Access Object
     */
    public abstract EventWorkerCrossDao eventWorkerCrossDao();

    /**
     * @return access to EventSupplyCross Data Access Object
     */
    public abstract EventSupplyCrossDao eventSupplyCrossDao();


    // NOTE(Krystian) wykonywanie jakichkolwiek dzialan na bazie musi zostac umieszczone w
    // Executors.newSingleThreadExecutor().execute(()->{kod ktory ma sie wykonac});
    // dzieki temu nie ma problemów z iloscia wykorzystywanych watkow


}
