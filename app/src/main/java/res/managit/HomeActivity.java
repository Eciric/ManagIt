package res.managit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;

import static res.managit.dbo.DatabaseFunctions.reloadDatabaseNames;
import static res.managit.dbo.DatabaseFunctions.reloadDatabases;
import static res.managit.dbo.DatabaseFunctions.saveDatabaseNames;

/**
 * Class which represents home - main activity of the app
 */
public class HomeActivity extends AppCompatActivity {

    /**
     * Function to initialize all variables required to manage databases
     * @param savedInstanceState activity bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PublicDatabaseAcces publicDatabaseAcces = new PublicDatabaseAcces();
        PublicDatabaseAcces.databaseList = new ArrayList<WarehouseDb>();
        PublicDatabaseAcces.databaseNameList = new ArrayList<String>();
        reloadDatabaseNames(getApplicationContext(), "DatabaseNames.csv");
        reloadDatabases(getApplicationContext());

        // Odkomentuj aby wypelnic baze danych przykladowymi rekordami
//       new DatabaseInitializer(PublicDatabaseAcces.databaseList.get(0)).execute();
        //Odkomentuj aby stworzyc przykladowa baze danych z przykladowymi rekordami
//        new DatabaseExampleCreator(getApplicationContext()).execute();


        if (getIntent().getBooleanExtra("EXIT", false)) {
            saveDatabaseNames(getApplicationContext(), "DatabaseNames.csv");
            finish();
        }
    }
}