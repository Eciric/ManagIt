package res.managit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Product;

import static res.managit.dbo.DatabaseFunctions.createDatabase;
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

        if (getIntent().getBooleanExtra("EXIT", false)) {
            saveDatabaseNames(getApplicationContext(),"DatabaseNames.csv");
            finish();
        }
    }
}
