package res.managit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import org.w3c.dom.Text;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Event;
import res.managit.materials.DrawableUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link planerFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class planerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //added calendar variable
    CalendarView calendarView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public planerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment planerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static planerFragment newInstance(String param1, String param2) {
        planerFragment fragment = new planerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_planer, container, false);
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        //list with calendar events not databases
        List<EventDay> events = new ArrayList<>();


        //TODO ot jest dla przykladu, ale trzeba znalezc sposob jak pobrac ktora baza została wybrana i to na niej pracowac
        WarehouseDb db = PublicDatabaseAcces.getDatabaseList().get(3);


        Executors.newSingleThreadExecutor().execute(()->{
            List<Event> eventList = db.eventDao().getAll();
        for(Event event : eventList){
            LocalDateTime date = event.getDate();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(date.getYear(),date.getMonthValue()-1,date.getDayOfMonth(),date.getHour(),date.getMinute());
            //TODO nie ustaiwa sie godzina w calendar
            //TODO daje tylko L albo UL jako znaczek przy dacie jak chcesz to mozeszz cos z tym pokombinowac
//            System.out.println("Godz ev: " +date.getHour() +":"+ date.getMinute());
//            System.out.println("Godz cal: " +calendar1.get(Calendar.HOUR) +":"+ calendar1.get(Calendar.MINUTE) );
            events.add(new EventDay(calendar1, DrawableUtils.getCircleDrawableWithText(getActivity().getApplicationContext(), event.getAction().equals("loading") ? "L":"UL")));

        }
        });

        //set how many months we can see. In that case 1 previous and 5 next
        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -1);
        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 5);
        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);


        //add icons/events to dates
        calendarView.setEvents(events);
        TextView textView = (TextView) view.findViewById(R.id.Text);
        calendarView.setOnDayClickListener(eventDay ->
                Executors.newSingleThreadExecutor().execute(()->{
                    List<Event> eventList = db.eventDao().getAll();
                    LocalDateTime dateTime = LocalDateTime.of(eventDay.getCalendar().get(Calendar.YEAR), eventDay.getCalendar().get(Calendar.MONTH)+1, eventDay.getCalendar().get(Calendar.DATE),eventDay.getCalendar().get(Calendar.HOUR), eventDay.getCalendar().get(Calendar.MINUTE));
                    //TODO nie ustawia sie godzina w dacie w kalendarzu wiec ten if nigdy nie zadziala bo o ile daty sie zgodza to godz zawsze jest 00:00
                    //TODO wyswietla sie tylko toString trzeba to jakos ladnie rozmiescic a nie tak jak ja nasrane tekstu
                    //TODO dodanie przycisku do dodawania eventu: przycisk -> nowy fragment
//                    System.out.println("Godz cal: " +eventDay.getCalendar().get(Calendar.HOUR) +":"+ eventDay.getCalendar().get(Calendar.MINUTE) );
                    for(Event event : eventList){
                        if(event.getDate().isEqual(dateTime)){
                            //trzeba ustawiac text poza tym Executors bo inaczej wywala error z tym ze tylko głowny watek moze miec dostep do view
                            setEventText(textView,event.toString());
                            break;
                        }
                    }

                })
        );


        return view;
    }

    //function to set text in main thread
    private void setEventText(final TextView text, String value){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

}