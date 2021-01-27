package res.managit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

import res.managit.add.event.adapter.EventAdapter;
import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Event;
import res.managit.dbo.relations.TypeAction;
import res.managit.materials.DrawableUtils;
import res.managit.service.EventRetriever;


/**
 * Class which represents planner fragment
 */
public class planerFragment extends Fragment {

    //added calendar variable
    private CalendarView calendarView;
    private EventAdapter adapterToEventsList;

    public planerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Thread which run function which add events to calendar as a icons
     *
     * @param events list with all events for selected database
     * @param db     selected database
     */
    public void createRunAndJoinThreadToLoadingCalendarView(List<EventDay> events, WarehouseDb db) {
        Thread t1 = new Thread(() -> {
            loadingEventsToCalendarView(events, db);
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function which is responsible for set all fragment UI.
     * It also set planner calendar and liseners which react when we pick date or event for selected date
     *
     * @param inflater           object that can be used to inflate any views in the fragment
     * @param container          this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState fragment's bundle
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Update the toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Planner");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_planer, container, false);
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        //list with calendar events not databases
        List<EventDay> events = Collections.synchronizedList(new ArrayList<>());
        WarehouseDb db = PublicDatabaseAcces.currentDatabase;
        createRunAndJoinThreadToLoadingCalendarView(events, db);

        //set how many months we can see. In that case 1 previous and 5 next
        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -1);
        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 5);
        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        //add icons/events to dates
        calendarView.setEvents(events);

        //initialize variable to events lists
        ListView listEvents = (ListView) view.findViewById(R.id.eventsList);
        ArrayList<Event> eventsListInCurrentDate = new ArrayList<>();
        adapterToEventsList = new EventAdapter(this.requireContext(), eventsListInCurrentDate);
        listEvents.setAdapter(adapterToEventsList);

        listEvents.setOnItemClickListener((adapter, v, position, arg3) -> {

            View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.event_popup, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            popupWindow.showAsDropDown(popupView, 0, 0);

            Button close = popupView.findViewById(R.id.close);
            close.setOnClickListener((event) -> {
                popupWindow.dismiss();
            });
            Button delete = popupView.findViewById(R.id.delete);

            Event value = (Event) adapter.getItemAtPosition(position);

            delete.setOnClickListener((event) -> {

                Thread deleteEvent = new Thread(() -> {
                    db.eventDao().deleteEvent(value);
                });
                deleteEvent.start();
                try {
                    deleteEvent.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                popupWindow.dismiss();
                setEventList(new ArrayList<>());
                onResume();
            });


            new EventRetriever(popupView, PublicDatabaseAcces.currentDatabase, value).execute();

        });


        calendarView.setOnDayClickListener(eventDay ->
                Executors.newSingleThreadExecutor().execute(() -> {
                    List<Event> eventList = db.eventDao().getAll();
                    List<Event> chosenEvents = new ArrayList<>();
                    LocalDateTime dateTime = LocalDateTime.of(eventDay.getCalendar()
                                    .get(Calendar.YEAR), eventDay
                                    .getCalendar()
                                    .get(Calendar.MONTH) + 1,
                            eventDay.getCalendar().get(Calendar.DATE),
                            eventDay.getCalendar().get(Calendar.HOUR),
                            eventDay.getCalendar().get(Calendar.MINUTE));

                    for (Event event : eventList) {
                        if (isEqualYearDay(dateTime, event)) {
                            chosenEvents.add(event);
                        }
                    }
                    setEventList(chosenEvents);
                })
        );

        //set button to add event
        FloatingActionButton fab = view.findViewById(R.id.add_event_button_start_activity);
        fab.setOnClickListener(view1 -> openAddEventFirstStepActivity());
        return view;
    }

    /**
     * Function to check that date in event and dataTime are equal
     *
     * @param dateTime date to compare
     * @param event    selected event to check
     * @return true if dates are equal or false if not
     */
    private boolean isEqualYearDay(LocalDateTime dateTime, Event event) {
        return event.getDate().getDayOfYear() == dateTime.getDayOfYear() && event.getDate().getYear() == dateTime.getYear();
    }

    /**
     * Function which add events to calendar as a icons
     *
     * @param events list with all events for selected database
     * @param db     selected database
     */
    private void loadingEventsToCalendarView(List<EventDay> events, WarehouseDb db) {
        List<Event> eventList = db.eventDao().getAll();
        for (Event event : eventList) {
            LocalDateTime date = event.getDate();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(date.getYear(),
                    date.getMonthValue() - 1,
                    date.getDayOfMonth(),
                    date.getHour(),
                    date.getMinute());
            events.add(new EventDay(calendar1, DrawableUtils.getCircleDrawableWithText(requireActivity()
                            .getApplicationContext(),
                    event.getAction().equals(TypeAction.Loading.label) ? "L" : "UL")));
        }
    }


    /**
     * Function to set text in main thread
     *
     * @param events list of events
     */
    private void setEventList(List<Event> events) {
        requireActivity().runOnUiThread(() -> {
            adapterToEventsList.clear();
            adapterToEventsList.addAll(events);
        });
    }

    /**
     * Function which responses for starting activity which create new event
     */
    private void openAddEventFirstStepActivity() {
        Intent intent = new Intent(getActivity(), AddEventFirstStepActivity.class);
        startActivity(intent);
    }


    @Override
    public void onResume() {
        super.onResume();
        List<EventDay> events = Collections.synchronizedList(new ArrayList<>());

        WarehouseDb db = PublicDatabaseAcces.currentDatabase;
        createRunAndJoinThreadToLoadingCalendarView(events, db);
        calendarView.setEvents(events);
    }

}