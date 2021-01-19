package res.managit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import res.managit.dbo.PublicDatabaseAcces;

import static res.managit.dbo.DatabaseFunctions.downloadDatabaseBackUp;
import static res.managit.dbo.DatabaseFunctions.exportDatabase;

/**
 * Class which represents activity with flyout menu
 */
public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    /**
     * Function used to initialize menu' activity ui.
     *
     * @param savedInstanceState fragment's bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String dbName = getIntent().getStringExtra("dbName");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(dbName);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new planerFragment()).commit();

        drawer = findViewById(R.id.drawer_activity);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_planer:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new planerFragment()).commit();
                break;
            case R.id.nav_manage:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new manageFragment()).commit();
                break;
            case R.id.nav_backup:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new backUpFragment()).commit();
                break;
            case R.id.nav_lowinstock:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new statisticsFragment()).commit();
                break;
            case R.id.nav_export:
                exportDatabase(PublicDatabaseAcces.currentDatabaseName);
                Toast.makeText(this, "Database exported", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_quit:
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}