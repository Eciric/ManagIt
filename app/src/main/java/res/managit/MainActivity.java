package res.managit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
import res.managit.dbo.entity.EventItem;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;

import res.managit.dbo.helpers.DatabaseExampleCreator;
import res.managit.dbo.helpers.DatabaseInitializer;
import res.managit.dbo.relations.manytomany.cross.EventCustomerCross;
import res.managit.dbo.relations.manytomany.cross.EventSupplyCross;
import res.managit.dbo.relations.manytomany.cross.EventWorkerCross;


import static res.managit.dbo.DatabaseFunctions.createDatabase;
import static res.managit.dbo.DatabaseFunctions.reloadDatabaseNames;
import static res.managit.dbo.DatabaseFunctions.reloadDatabases;
import static res.managit.dbo.DatabaseFunctions.saveDatabaseNames;

/**
 * Class which represents main activity - loading screen
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Function which initialize main activity UI and show loading screen for 3 sec
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
