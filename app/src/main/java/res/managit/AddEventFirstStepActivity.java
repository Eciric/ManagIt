package res.managit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import res.managit.add.event.adapter.WorkerAdapter;
import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Worker;
import res.managit.wizard.event.DatePickerFragment;
import res.managit.wizard.event.TimePickerFragment;


public class AddEventFirstStepActivity extends AppCompatActivity implements OnItemSelectedListener {

    private ListView listWorkers;
    private ArrayList<Worker> workersArrayList;
    private static WorkerAdapter workerAdapter;

    public static WorkerAdapter getWorkerAdapter() {
        return workerAdapter;
    }

    public static void setWorkerAdapter(WorkerAdapter workerAdapter) {
        AddEventFirstStepActivity.workerAdapter = workerAdapter;
    }

    private Button buttonNextStep;

    private String spinnerTypeAction;

    private static Integer hourOfDayEvent;
    private static Integer minuteEvent;
    private static Integer yearEvent;
    private static Integer monthEvent;
    private static Integer dayEvent;

    public static LocalDateTime getDateTime() {
        return dateTime;
    }

    public static void setDateTime(LocalDateTime dateTime) {
        AddEventFirstStepActivity.dateTime = dateTime;
    }

    private static LocalDateTime dateTime;

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

//        Load - zaladunek z magazynu - ustawiam workersow - ustawiam customersow - odejmuje przedmioty
//        Unload - rozladunek w magazynie - ustawaim workersow - ustawiam suppliersow - dodaje przedmioty

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Load");
        categories.add("Unload");
        spinnerTypeAction = "Load";
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout_formatter, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        //initialize for workers
        listWorkers = (ListView) findViewById(R.id.workersList);
        workersArrayList = new ArrayList<>();
        WarehouseDb db = PublicDatabaseAcces.currentDatabase;
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Worker> workerList = db.workerDao().getAll();
            workersArrayList.addAll(workerList);
        });
        workerAdapter = new WorkerAdapter(workersArrayList, getApplicationContext());
        listWorkers.setAdapter(workerAdapter);

        buttonNextStep = (Button) findViewById(R.id.buttonNextStep);
        buttonNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllAssigned()) {
                    monthEvent += 1;
                    String monthString = monthEvent >= 10 ? monthEvent.toString() : "0" + monthEvent.toString();
                    String minuteString = minuteEvent >= 10 ? minuteEvent.toString() : "0" + minuteEvent.toString();
                    String hourString = hourOfDayEvent >= 10 ? hourOfDayEvent.toString() : "0" + hourOfDayEvent.toString();
                    String dayString = dayEvent >= 10 ? dayEvent.toString() : "0" + dayEvent.toString();
                    String yearString = yearEvent.toString();
                    String timeInString = yearString + "-" + monthString + "-" + dayString + " " + hourString + ":" + minuteString;

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    dateTime = LocalDateTime.parse(timeInString, formatter);
                    AddEventFirstStepActivity.this.finish();

                    Intent intent = spinnerTypeAction.equals("Load") ?
                            new Intent(AddEventFirstStepActivity.this, AddEventSecondStepLoadActivity.class) :
                            new Intent(AddEventFirstStepActivity.this, AddEventSecondStepUnloadActivity.class);
                    startActivity(intent);
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Please assigned all information!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
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
                && dayEvent != null;
    }
}