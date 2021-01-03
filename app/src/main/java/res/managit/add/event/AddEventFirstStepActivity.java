package res.managit.add.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import res.managit.AddEventSecondStepActivity;
import res.managit.R;
import res.managit.add.event.adapter.CustomerAdapter;
import res.managit.add.event.adapter.SupplierAdapter;
import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.Supply;
import res.managit.settings.Settings;
import res.managit.wizard.event.DatePickerFragment;
import res.managit.wizard.event.TimePickerFragment;


public class AddEventFirstStepActivity extends AppCompatActivity implements OnItemSelectedListener {

    private ListView listCustomers;
    private ArrayList<Customer> customerArrayList;
    private static CustomerAdapter customerAdapter;

    private ListView listSuppliers;
    private ArrayList<Supply> suppliersArrayList;
    private static SupplierAdapter supplierAdapter;

    private Button buttonNextStep;
    private CheckBox checkBoxCustomer;

    private String spinnerTypeAction;

    private static Integer hourOfDayEvent;
    private static Integer minuteEvent;
    private static Integer yearEvent;
    private static Integer monthEvent;
    private static Integer dayEvent;

    public static Integer getYearEvent() {
        return yearEvent;

    }

    public static void setYearEvent(Integer yearEvent) {
        AddEventFirstStepActivity.yearEvent = yearEvent;
    }

    public static Integer getMonthEvent() {
        return monthEvent;
    }

    public static void setMonthEvent(Integer monthEvent) {
        AddEventFirstStepActivity.monthEvent = monthEvent;
    }

    public static Integer getDayEvent() {
        return dayEvent;
    }

    public static void setDayEvent(Integer day) {
        AddEventFirstStepActivity.dayEvent = day;
    }

    public static Integer getHourOfDayEvent() {
        return hourOfDayEvent;
    }

    public static void setHourOfDayEvent(Integer hourOfDayEvent) {
        AddEventFirstStepActivity.hourOfDayEvent = hourOfDayEvent;
    }

    public static Integer getMinuteEvent() {
        return minuteEvent;
    }

    public static void setMinuteEvent(Integer minuteEvent) {
        AddEventFirstStepActivity.minuteEvent = minuteEvent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_first_step);

        hourOfDayEvent = null;
        minuteEvent = null;
        yearEvent = null;
        monthEvent = null;
        dayEvent = null;
        // initialize choosing type event

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinnerTypeEvent);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Load");
        categories.add("Unload");
        spinnerTypeAction = "Load";
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

        buttonNextStep = (Button) findViewById(R.id.buttonNextStep);
        buttonNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllAssigned()) {
//                    System.out.println("wszystko git");
//                    String monthString = monthEvent > 10 ? monthEvent.toString() : "0" + monthEvent.toString();
//                    String minuteString = minuteEvent > 10 ? minuteEvent.toString() : "0" + minuteEvent.toString();
//                    String hourString = hourOfDayEvent > 10 ? hourOfDayEvent.toString() : "0" + hourOfDayEvent.toString();
//                    String dayString = dayEvent > 10 ? dayEvent.toString() : "0" + dayEvent.toString();
//                    String yearString = yearEvent.toString();
//                    String timeInString = yearString + "-" + monthString + "-" + minuteString + " " + hourString + ":" + minuteString;
//
//                    Gson gson = new Gson();
//
//
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//                    LocalDateTime dateTime = LocalDateTime.parse(timeInString, formatter);
//                    Event event = new Event(dateTime, spinnerTypeAction, 0, l)
//                    String json = gson.toJson(myObj);
                    Intent intent = new Intent(AddEventFirstStepActivity.this, AddEventSecondStepActivity.class);
//                    if (1)
//                        2
                    startActivity(intent);
                } else {
                    System.out.println("nie moge przejs");
                }
            }
        });

    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        spinnerTypeAction = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public boolean isAllAssigned() {
        return hourOfDayEvent != null
                && minuteEvent != null
                && monthEvent != null
                && yearEvent != null
                && dayEvent != null
                && SupplierAdapter.getSuppliesListChecked().size() != 0
                && CustomerAdapter.getCustomerListChecked().size() != 0;
    }
}