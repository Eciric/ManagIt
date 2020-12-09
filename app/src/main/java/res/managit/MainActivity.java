package res.managit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Product;

import static res.managit.dbo.DatabaseFunctions.createDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PublicDatabaseAcces publicDatabaseAcces = new PublicDatabaseAcces();
        PublicDatabaseAcces.databaseList = new ArrayList<WarehouseDb>();
        PublicDatabaseAcces.databaseNameList = new ArrayList<String>();


        //(KRYSTIAN) trzeba poprawic by nie dodawac takis samych elementow w niektorzych kategoriach -  cos z IGNORE - done
        //(KRYSTIAN) Tworza sie bazy o takich samych nazwach - do zmiany - done
        //(KRYSTIAN) wyprobowac inna metode kreatora bazy - ta moze wykorzystywac za duzo wątków - done

    }
}