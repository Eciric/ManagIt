package res.managit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;
import res.managit.dbo.helpers.DatabaseInitializer;
import res.managit.dbo.relations.manytomany.cross.EventCustomerCross;
import res.managit.dbo.relations.manytomany.cross.EventProductCross;
import res.managit.dbo.relations.manytomany.cross.EventSupplyCross;
import res.managit.dbo.relations.manytomany.cross.EventWorkerCross;

import static res.managit.dbo.DatabaseFunctions.createDatabase;
import static res.managit.dbo.DatabaseFunctions.exportDatabase;

import static res.managit.dbo.DatabaseFunctions.importDatabase;
import static res.managit.dbo.DatabaseFunctions.reloadDatabaseNames;
import static res.managit.dbo.DatabaseFunctions.reloadDatabases;
import static res.managit.dbo.DatabaseFunctions.saveDatabaseNames;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PublicDatabaseAcces publicDatabaseAcces = new PublicDatabaseAcces();
        PublicDatabaseAcces.databaseList = new ArrayList<WarehouseDb>();
        PublicDatabaseAcces.databaseNameList = new ArrayList<String>();
        reloadDatabaseNames(getApplicationContext(),"DatabaseNames.csv");
        reloadDatabases(getApplicationContext());

        // Odkomentuj aby wypelnic baze danych przykladowymi rekordami
        // new DatabaseInitializer(PublicDatabaseAcces.databaseList.get(0)).execute();

        //category
        Category category1 = new Category("Czyszczenie szyb");
        Category category2 = new Category("Filtry");
        Category category3 = new Category("Klimatyzacja");
        Category category4 = new Category("Nadwozie");

        //Product
        Product product1 = new Product("Dysza spryskiwacza",5,1);
        Product product2 = new Product("Narzedzia",100,1);
        Product product3 = new Product("Wycieraczki",500,1);
        Product product4 = new Product("Filtr oleju",60,2);
        Product product5 = new Product("Filtr powietrza",44,2);
        Product product6 = new Product("Osuszacz",10,3);
        Product product7 = new Product("Parownik",18,3);
        Product product8 = new Product("Zderzak",20,4);
        Product product9 = new Product("Blotnik",36,4);
        Product product10 = new Product("Listwy ozdobne",15,4);

        //Contact
        Contact contact1 = new Contact("Kwiatowa",15,97222,"Lodz","Polska","987654321");
        Contact contact2 = new Contact("Sloneczna",22,97225,"Lodz","Polska","123456789");
        Contact contact3 = new Contact("Nowa",57,90258,"Tomaszow","Polska","589623748");
        Contact contact4 = new Contact("Piekna",95,95693,"Warszawa","Polska","598475692");
        Contact contact5 = new Contact("Zelazna",62,98586,"Kielce","Polska","548921458");
        Contact contact6 = new Contact("Zlota",15,98669,"Nowy Sacz","Polska","156987563");

        //Worker
        Worker worker1 = new Worker("Marek","Kowalski","manager",4);
        Worker worker2 = new Worker("Arek","Nowak","forklift",2);
        Worker worker3 = new Worker("Karol","Zulczyk","forklift",1);

        //Supplier
        Supply supply1 = new Supply("iParts",6);
        Supply supply2 = new Supply("CarStore",5);

        //Customer
        Customer customer1 = new Customer("Jan",3);

        //Event
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        Event event1 = new Event(LocalDateTime.parse("20-12-2020 10:00",formatter),"loading",5, new ArrayList<Long>(Arrays.asList(2l,3l)),new ArrayList<Long>(Arrays.asList(1l,2l)), new ArrayList<Long>(Arrays.asList(1l)),new ArrayList<Long>(Arrays.asList(1l,5l,8l)));
        Event event2 = new Event(LocalDateTime.parse("05-01-2021 15:00",formatter),"unloading",6, new ArrayList<Long>(Arrays.asList(2l,3l)),new ArrayList<Long>(Arrays.asList(2l)), new ArrayList<Long>(Arrays.asList()),new ArrayList<Long>(Arrays.asList(6l,9l,10l)));
        Event event3 = new Event(LocalDateTime.parse("15-03-2021 11:30",formatter),"loading",1, new ArrayList<Long>(Arrays.asList(3l)),new ArrayList<Long>(Arrays.asList(1l)), new ArrayList<Long>(Arrays.asList()),new ArrayList<Long>(Arrays.asList(1l,2l,3l,4l,6l,8l,9l,10l)));

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
        EventCustomerCross eventCustomerCross1  = new EventCustomerCross(1, 1);

        //EventProduct
        EventProductCross eventProductCross1 = new EventProductCross(1,1);
        EventProductCross eventProductCross2 = new EventProductCross(1,5);
        EventProductCross eventProductCross3 = new EventProductCross(1,8);
        EventProductCross eventProductCross4 = new EventProductCross(2,6);
        EventProductCross eventProductCross5 = new EventProductCross(2,9);
        EventProductCross eventProductCross6 = new EventProductCross(2,10);
        EventProductCross eventProductCross7 = new EventProductCross(3,1);
        EventProductCross eventProductCross8 = new EventProductCross(3,2);
        EventProductCross eventProductCross9 = new EventProductCross(3,3);
        EventProductCross eventProductCross10 = new EventProductCross(3,4);
        EventProductCross eventProductCross11 = new EventProductCross(3,6);
        EventProductCross eventProductCross12 = new EventProductCross(3,8);
        EventProductCross eventProductCross13 = new EventProductCross(3,9);
        EventProductCross eventProductCross14 = new EventProductCross(3,10);

//        //database create
//        WarehouseDb db = createDatabase(getApplicationContext(),"CarWarehouse");
//
//        //Insert Category
//        Executors.newSingleThreadExecutor().execute(()->{db.categoryDao().insertCategory(category1, category2, category3, category4);});
//
//        //Insert Product
//        Executors.newSingleThreadExecutor().execute(()->{ db.productDao().insertProduct(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10);});
//
//        //Insert Contact
//        Executors.newSingleThreadExecutor().execute(()->{db.contactDao().insertContact(contact1, contact2, contact3, contact4, contact5, contact6);});
//
//        //Insert Worker
//        Executors.newSingleThreadExecutor().execute(()->{db.workerDao().insertWorker(worker1, worker2, worker3);});
//
//        //Insert Supplier
//        Executors.newSingleThreadExecutor().execute(()->{db.supplyDao().insertSupply(supply1, supply2);});
//
//        //Insert Customer
//        Executors.newSingleThreadExecutor().execute(()->{db.customerDao().insertCustomer(customer1);});
//
//        //Insert Event
//        Executors.newSingleThreadExecutor().execute(()->{db.eventDao().insertEvent(event1,event2,event3);});
//
//        //Inset EventWorker
//        Executors.newSingleThreadExecutor().execute(()->{db.eventWorkerCrossDao().insertEventWorkerCross(eventWorkerCross1, eventWorkerCross2, eventWorkerCross3, eventWorkerCross4, eventWorkerCross5);});
//        //Inset EventSupplier
//        Executors.newSingleThreadExecutor().execute(()->{db.eventSupplyCrossDao().insertEventSupplyCross(eventSupplyCross1, eventSupplyCross2, eventSupplyCross3, eventSupplyCross4);});
//        //Inset EventCustomer
//        Executors.newSingleThreadExecutor().execute(()->{db.eventCustomerCrossDao().insertEventCustomerCross(eventCustomerCross1);});
//        //Inset EventProduct
//        Executors.newSingleThreadExecutor().execute(()->{db.eventProductCrossDao().insertEventProductCross(eventProductCross1, eventProductCross2, eventProductCross3, eventProductCross4, eventProductCross5, eventProductCross6, eventProductCross7, eventProductCross8, eventProductCross9, eventProductCross10, eventProductCross11, eventProductCross12, eventProductCross13, eventProductCross14);});


        if (getIntent().getBooleanExtra("EXIT", false)) {
            saveDatabaseNames(getApplicationContext(),"DatabaseNames.csv");
            finish();
        }
    }
}
