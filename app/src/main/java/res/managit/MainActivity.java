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



        if (getIntent().getBooleanExtra("EXIT", false)) {
            saveDatabaseNames(getApplicationContext(),"DatabaseNames.csv");
            finish();
        }
    }
}
