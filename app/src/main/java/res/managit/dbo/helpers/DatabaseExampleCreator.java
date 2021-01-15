package res.managit.dbo.helpers;

import android.content.Context;
import android.os.AsyncTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import res.managit.dbo.relations.manytomany.cross.EventCustomerCross;
import res.managit.dbo.relations.manytomany.cross.EventSupplyCross;
import res.managit.dbo.relations.manytomany.cross.EventWorkerCross;

import static res.managit.dbo.DatabaseFunctions.createDatabase;

/**
 * Class created to create example Warehouse database with inserting all example data to tables
 */
public class DatabaseExampleCreator extends AsyncTask<Void, Void, Void> {
    Context context;

    /**
     * Function which set local context - variable which stores all data connected with android app
     * @param context
     */
    public DatabaseExampleCreator(Context context) {
        this.context = context;
    }

    /**
     * Function which do task in background thread
     * @param voids
     * @return null variable
     */
    @Override
    protected Void doInBackground(Void... voids) {
        //category
        Category category1 = new Category("Czyszczenie szyb");
        Category category2 = new Category("Filtry");
        Category category3 = new Category("Klimatyzacja");
        Category category4 = new Category("Nadwozie");

        //Product
        Product product1 = new Product("Dysza spryskiwacza", 5, 1);
        Product product2 = new Product("Narzedzia", 100, 1);
        Product product3 = new Product("Wycieraczki", 500, 1);
        Product product4 = new Product("Filtr oleju", 60, 2);
        Product product5 = new Product("Filtr powietrza", 44, 2);
        Product product6 = new Product("Osuszacz", 10, 3);
        Product product7 = new Product("Parownik", 18, 3);
        Product product8 = new Product("Zderzak", 20, 4);
        Product product9 = new Product("Blotnik", 36, 4);
        Product product10 = new Product("Listwy ozdobne", 15, 4);


        //Contact
        Contact contact1 = new Contact("Kwiatowa", 15, 97222, "Lodz", "Polska", "987654321");
        Contact contact2 = new Contact("Sloneczna", 22, 97225, "Lodz", "Polska", "123456789");
        Contact contact3 = new Contact("Nowa", 57, 90258, "Tomaszow", "Polska", "589623748");
        Contact contact4 = new Contact("Piekna", 95, 95693, "Warszawa", "Polska", "598475692");
        Contact contact5 = new Contact("Zelazna", 62, 98586, "Kielce", "Polska", "548921458");
        Contact contact6 = new Contact("Zlota", 15, 98669, "Nowy Sacz", "Polska", "156987563");

        //Worker
        Worker worker1 = new Worker("Marek", "Kowalski", "manager", 4);
        Worker worker2 = new Worker("Arek", "Nowak", "forklift", 2);
        Worker worker3 = new Worker("Karol", "Zulc3zyk", "forklift", 1);
        Worker worker4 = new Worker("Karol1", "Zulc2zyk", "for2klift", 18);
        Worker worker5 = new Worker("Karol2", "Zulc4zyk", "fork1lift", 13);
        Worker worker6 = new Worker("Karol3", "Zulc1zyk", "fork2lift", 11);
        Worker worker7 = new Worker("Karol4", "Zulc2zyk", "fork2lift", 14);
        Worker worker8 = new Worker("Karol5", "Z4ulczyk", "for4klift", 15);
        Worker worker9 = new Worker("Karol6", "Zulc2zyk", "fork1lift", 16);
        Worker worker10 = new Worker("Karol7", "Zurlczyk", "forkl23ift", 17);

        Worker worker11 = new Worker("Marek1", "Kowalski1", "man1ager", 4);
        Worker worker12 = new Worker("Are1k", "Nowa1k", "forkli2ft", 2);
        Worker worker13 = new Worker("Karol1", "Zulc3zyk3", "forklift", 1);
        Worker worker14 = new Worker("Karol12", "Zulc2zyk4", "for2klift", 18);
        Worker worker15 = new Worker("Karol23", "Zulc4zyk5", "fork1lift", 13);
        Worker worker16 = new Worker("Karol33", "Zulc1zyk6", "fork2lift", 11);
        Worker worker17 = new Worker("Karol44", "Zulc2zyk7", "fork2lift", 14);
        Worker worker18 = new Worker("Karol55", "Z4ulczyk8", "for4klift", 15);
        Worker worker19 = new Worker("Karol66", "Zulc2zyk9", "fork1lift", 16);
        Worker worker20 = new Worker("Karol77", "Zurlczyk0", "forkl23ift", 17);

        //Supplier
        Supply supply1 = new Supply("iParts", 6);
        Supply supply2 = new Supply("CarStore", 5);

        //Customer
        Customer customer1 = new Customer("Jan", 3);
        Customer customer2 = new Customer("Marek", 4);
        Customer customer3 = new Customer("Krystian", 5);

        //EventItem
        EventItem eventItem1 = new EventItem(50, 1, 1);
        EventItem eventItem2 = new EventItem(150, 5, 1);
        EventItem eventItem3 = new EventItem(80, 8, 1);

        EventItem eventItem4 = new EventItem(90, 6, 2);
        EventItem eventItem5 = new EventItem(58, 9, 2);
        EventItem eventItem6 = new EventItem(62, 10, 2);

        EventItem eventItem7 = new EventItem(52, 1, 3);
        EventItem eventItem8 = new EventItem(90, 2, 3);
        EventItem eventItem9 = new EventItem(12, 3, 3);
        EventItem eventItem10 = new EventItem(52, 4, 3);
        EventItem eventItem11 = new EventItem(62, 6, 3);
        EventItem eventItem12 = new EventItem(72, 8, 3);
        EventItem eventItem13 = new EventItem(62, 9, 3);
        EventItem eventItem14 = new EventItem(12, 10, 3);

        //Event
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        Event event1 = new Event(LocalDateTime.parse("01-01-2021 10:00", formatter), "loading", new ArrayList<Long>(Arrays.asList(2l, 3l)), new ArrayList<Long>(Arrays.asList(1l, 2l)), new ArrayList<Long>(Arrays.asList(1l)), false);
        Event event2 = new Event(LocalDateTime.parse("02-01-2021 15:00", formatter), "unloading", new ArrayList<Long>(Arrays.asList(2l, 3l)), new ArrayList<Long>(Arrays.asList(2l)), new ArrayList<Long>(Arrays.asList()), false);
        Event event3 = new Event(LocalDateTime.parse("03-01-2021 11:30", formatter), "loading", new ArrayList<Long>(Arrays.asList(3l)), new ArrayList<Long>(Arrays.asList(1l)), new ArrayList<Long>(Arrays.asList()), false);
        //Event event4 = new Event(LocalDateTime.parse("15-03-2021 14:30",formatter),"unloading",1, new ArrayList<Long>(Arrays.asList(3l)),new ArrayList<Long>(Arrays.asList(1l)), new ArrayList<Long>(Arrays.asList()),new ArrayList<Long>(Arrays.asList(1l,2l,3l,4l,6l,8l,9l,10l)));

        //EventWorker
        EventWorkerCross eventWorkerCross1 = new EventWorkerCross(1, 2);
        EventWorkerCross eventWorkerCross2 = new EventWorkerCross(1, 3);
        EventWorkerCross eventWorkerCross3 = new EventWorkerCross(2, 2);
        EventWorkerCross eventWorkerCross4 = new EventWorkerCross(2, 3);
        EventWorkerCross eventWorkerCross5 = new EventWorkerCross(3, 3);

        //EventSupplier
        EventSupplyCross eventSupplyCross1 = new EventSupplyCross(1, 1);
        EventSupplyCross eventSupplyCross2 = new EventSupplyCross(1, 2);
        EventSupplyCross eventSupplyCross3 = new EventSupplyCross(2, 2);
        EventSupplyCross eventSupplyCross4 = new EventSupplyCross(3, 1);

        //EventCustomer
        EventCustomerCross eventCustomerCross1 = new EventCustomerCross(1, 1);


        //database create
        WarehouseDb db = createDatabase(context, "CarWarehouse");

        //Insert Category
        db.categoryDao().insertCategory(category1, category2, category3, category4);

        //Insert Product
        db.productDao().insertProduct(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10);

        //Insert Contact
        db.contactDao().insertContact(contact1, contact2, contact3, contact4, contact5, contact6);

        //Insert Worker
        db.workerDao().insertWorker(worker1, worker2, worker3);

        //Insert Supplier
        db.supplyDao().insertSupply(supply1, supply2);

        //Insert Customer
        db.customerDao().insertCustomer(customer1, customer2, customer3);

        //Insert Event
        db.eventDao().insertEvent(event1, event2, event3);

        //Inset EventWorker
        db.eventWorkerCrossDao().insertEventWorkerCross(eventWorkerCross1, eventWorkerCross2, eventWorkerCross3, eventWorkerCross4, eventWorkerCross5);
        //Inset EventSupplier
        db.eventSupplyCrossDao().insertEventSupplyCross(eventSupplyCross1, eventSupplyCross2, eventSupplyCross3, eventSupplyCross4);
        //Inset EventCustomer
        db.eventCustomerCrossDao().insertEventCustomerCross(eventCustomerCross1);
        //Insert EventItem
        db.eventItemDao().insertEventItem(eventItem1, eventItem2, eventItem3, eventItem4, eventItem5, eventItem6, eventItem7, eventItem8, eventItem9, eventItem10, eventItem11, eventItem12, eventItem13, eventItem14);

        return null;
    }
}
