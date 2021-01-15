package res.managit.dbo.helpers;

import android.os.AsyncTask;

import java.time.LocalDateTime;
import java.util.Arrays;

import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.EventItem;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;

/* Tworzy pare przykladowych rekordow w tablicach dla bazy przekazanej w konstruktorze
 *  Wywolanie -> new DatabaseInitializer(PublicDatabaseAcces.databaseList.get( <indeks bazy danych> )).execute();
 *  UWAGA - przed dodaniem nowych rekordow wszystkie dotychczasowe rekordy sa USUWANE!
 * */
/**
 * Class created to insert example data to first Database in the databases list
 * WARNING: before inserting, all data from this database is removed
 */
public class DatabaseInitializer extends AsyncTask<Void, Void, Void> {
    private WarehouseDb db;

    /**
     * Function which set local Warehouse variable
     * @param db - Warehouse variable which represents Room database
     */
    public DatabaseInitializer(WarehouseDb db) {
        this.db = db;
    }

    /**
     * Function which do task in background thread
     * @param voids
     * @return null variable
     */
    @Override
    protected Void doInBackground(Void... voids) {
        db.categoryDao().deleteAll();
        db.categoryDao().insertCategory(new Category("Vegetable"));
        db.categoryDao().insertCategory(new Category("Meat"));
        db.categoryDao().insertCategory(new Category("Fruit"));

        db.productDao().deleteAll();
        Product product1 = new Product("Carrot", 10, db.categoryDao().getCategoryByName("Vegetable").getCategoryId());
        Product product2 = new Product("Salad", 45, db.categoryDao().getCategoryByName("Vegetable").getCategoryId());
        Product product3 = new Product("Beef", 76, db.categoryDao().getCategoryByName("Meat").getCategoryId());
        Product product4 = new Product("Banana", 21, db.categoryDao().getCategoryByName("Fruit").getCategoryId());
        Product product5 = new Product("Apple", 22, db.categoryDao().getCategoryByName("Fruit").getCategoryId());
        db.productDao().insertProduct(product1);
        db.productDao().insertProduct(product2);
        db.productDao().insertProduct(product3);
        db.productDao().insertProduct(product4);
        db.productDao().insertProduct(product5);

        db.contactDao().deleteAll();
        db.contactDao().insertContact(new Contact("Coma", 22, 33300, "London", "UK", "212031298"));
        db.contactDao().insertContact(new Contact("Milnee", 11, 45709, "Kingston", "UK", "458712321"));
        db.contactDao().insertContact(new Contact("Dockings", 45, 21242, "Los Angeles", "USA", "598216341"));
        db.contactDao().insertContact(new Contact("Roses", 2, 54908, "Hamburg", "Germany", "70356841"));
        db.contactDao().insertContact(new Contact("Elizabeth", 3, 33300, "London", "UK", "89367821"));
        db.contactDao().insertContact(new Contact("Corona", 8, 33211, "Dover", "UK", "793287631"));
        db.contactDao().insertContact(new Contact("Wall", 99, 65791, "Leeds", "UK", "643798157"));
        db.contactDao().insertContact(new Contact("Polna", 142, 98001, "Kazimierz Dolny", "Poland", "458999176"));

        db.workerDao().deleteAll();
        Worker worker1 = new Worker("Lukas", "King", "Manager", db.contactDao().getByPhoneNumber("458712321").getContactId());
        Worker worker2 = new Worker("Adam", "Moron", "Storekeeper", db.contactDao().getByPhoneNumber("643798157").getContactId());
        Worker worker3 = new Worker("Mori", "Pichetta", "Storekeeper", db.contactDao().getByPhoneNumber("89367821").getContactId());
        db.workerDao().insertWorker(worker1);
        db.workerDao().insertWorker(worker2);
        db.workerDao().insertWorker(worker3);

        db.supplyDao().deleteAll();
        Supply supplier1 = new Supply("Hortex", db.contactDao().getByPhoneNumber("212031298").getContactId());
        Supply supplier2 = new Supply("Dao Dao", db.contactDao().getByPhoneNumber("793287631").getContactId());
        db.supplyDao().insertSupply(supplier1);
        db.supplyDao().insertSupply(supplier2);

        db.customerDao().deleteAll();
        Customer customer1 = new Customer("Lidl", db.contactDao().getByPhoneNumber("598216341").getContactId());
        Customer customer2 = new Customer("Kaufland", db.contactDao().getByPhoneNumber("458999176").getContactId());
        db.customerDao().insertCustomer(customer1);
        db.customerDao().insertCustomer(customer2);

        db.eventItemDao().deleteAll();
        EventItem eventItem1 = new EventItem(34, 1, 1);
        EventItem eventItem2 = new EventItem(56, 3, 2);
        EventItem eventItem3 = new EventItem(78, 4, 2);
        db.eventItemDao().insertEventItem(eventItem1, eventItem2, eventItem3);


        // action: unloading - wjezdza dostawa, loading - produkt wyjezdza z magazynu
        db.eventDao().deleteAll();
        db.eventDao().insertEvent(new Event(LocalDateTime.now(), "unloading",
                Arrays.asList(1l, 2l),
                Arrays.asList(1l),
                Arrays.asList(),
                 false));
        db.eventDao().insertEvent(new Event(LocalDateTime.now(), "loading",
                Arrays.asList(3l),
                Arrays.asList(),
                Arrays.asList(2l, 1l),
                 false));
        return null;
    }
}
