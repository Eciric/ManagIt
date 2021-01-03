package res.managit.add.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import res.managit.R;
import res.managit.adaper.EventAdapter;
import res.managit.add.event.adapter.CustomerAdapter;
import res.managit.add.event.adapter.SupplierAdapter;
import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.Supply;
import res.managit.materials.DrawableUtils;
import res.managit.service.EventRetriever;
import res.managit.settings.Settings;
import res.managit.wizard.event.DatePickerFragment;
import res.managit.wizard.event.TimePickerFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.snackbar.Snackbar;


public class AddEventFirstStepActivity extends AppCompatActivity implements OnItemSelectedListener {

    private ListView listCustomers;
    private ArrayList<Customer> customerArrayList;
    private static CustomerAdapter customerAdapter;

    private ListView listSuppliers;
    private ArrayList<Supply> suppliersArrayList;
    private static SupplierAdapter supplierAdapter;

    private Button buttonNextStep;
    private CheckBox checkBoxCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_first_step);


        // initialize choosing type event

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinnerTypeEvent);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Load");
        categories.add("Unload");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        //initialize for customers
        listCustomers = (ListView) findViewById(R.id.customersList);
        customerArrayList = new ArrayList<>();
        WarehouseDb db = PublicDatabaseAcces.getDatabaseList().get(Settings.getActualSelectedDataBase());

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Customer> customerList = db.customerDao().getAll();
            customerArrayList.addAll(customerList);
        });
        customerAdapter = new CustomerAdapter(customerArrayList, getApplicationContext());
        listCustomers.setAdapter(customerAdapter);


        //initialize for suppliers
        listSuppliers = (ListView) findViewById(R.id.suppliersList);
        suppliersArrayList = new ArrayList<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Supply> supplyList = db.supplyDao().getAll();
            suppliersArrayList.addAll(supplyList);
        });
        supplierAdapter = new SupplierAdapter(suppliersArrayList, getApplicationContext());
        listSuppliers.setAdapter(supplierAdapter);

//        listSuppliers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Supply dataModel= suppliersArrayList.get(position);
//                dataModel.
//            }
//        });

        buttonNextStep = (Button) findViewById(R.id.buttonNextStep);
        buttonNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(SupplierAdapter.getSuppliesListChecked());
            }
        });

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}