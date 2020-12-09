package res.managit.dbo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import res.managit.dbo.converters.DateConverter;
import res.managit.dbo.converters.ListConverter;
import res.managit.dbo.dao.CategoryDao;
import res.managit.dbo.dao.ContactDao;
import res.managit.dbo.dao.CustomerDao;
import res.managit.dbo.dao.EventDao;
import res.managit.dbo.dao.ProductDao;
import res.managit.dbo.dao.SupplyDao;
import res.managit.dbo.dao.WorkerDao;
import res.managit.dbo.dao.cross.EventCustomerCrossDao;
import res.managit.dbo.dao.cross.EventProductCrossDao;
import res.managit.dbo.dao.cross.EventSupplyCrossDao;
import res.managit.dbo.dao.cross.EventWorkerCrossDao;
import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;
import res.managit.dbo.relations.manytomany.cross.EventCustomerCross;
import res.managit.dbo.relations.manytomany.cross.EventProductCross;
import res.managit.dbo.relations.manytomany.cross.EventSupplyCross;
import res.managit.dbo.relations.manytomany.cross.EventWorkerCross;

@Database(entities = {Category.class, Contact.class, Customer.class, Event.class, Product.class,
        Supply.class, Worker.class, EventProductCross.class, EventCustomerCross.class,
        EventSupplyCross.class, EventWorkerCross.class}, version = 1, exportSchema = false)
@TypeConverters({ListConverter.class, DateConverter.class})
public abstract class WarehouseDb extends RoomDatabase {
    public abstract CategoryDao categoryDao();

    public abstract ContactDao contactDao();

    public abstract CustomerDao customerDao();

    public abstract EventDao eventDao();

    public abstract ProductDao productDao();

    public abstract SupplyDao supplyDao();

    public abstract WorkerDao workerDao();

    public abstract EventCustomerCrossDao eventCustomerCrossDao();

    public abstract EventWorkerCrossDao eventWorkerCrossDao();

    public abstract EventSupplyCrossDao eventSupplyCrossDao();

    public abstract EventProductCrossDao eventProductCrossDao();

    //todo sprawdzic czy to zadziała bo sa problemy z budowaniem "Cannot access database on the main thread since"
//    private static volatile WarehouseDb INSTANCE;
//    private static final int NUMBER_OF_THREADS = 4;
//    static final ExecutorService databaseWriteExecutor =
//            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
//
//    static WarehouseDb getDatabase(final Context context, String name) {
//        if (INSTANCE == null) {
//            synchronized (WarehouseDb.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            WarehouseDb.class, name)
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }

}