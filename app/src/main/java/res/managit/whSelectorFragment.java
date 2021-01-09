package res.managit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.helpers.ExecuteEvents;

public class whSelectorFragment extends Fragment {
    ListView listView;
    NavController navController;
    AtomicBoolean tempToMenageExecutedThread = new AtomicBoolean(false);

    ExecuteEvents executeEvents;

    public whSelectorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        listView = view.findViewById(R.id.listview);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.listview_text_formatter, R.id.textView2, PublicDatabaseAcces.databaseNameList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {

            if (PublicDatabaseAcces.currentDatabase == null) {
                tempToMenageExecutedThread.set(true);
            }
            Intent in = new Intent(getActivity(), MenuActivity.class);
            in.putExtra("dbName", PublicDatabaseAcces.databaseNameList.get(i));
            PublicDatabaseAcces.currentDatabase = PublicDatabaseAcces.getDatabaseByName(PublicDatabaseAcces.databaseNameList.get(i));
            PublicDatabaseAcces.currentDatabaseName = PublicDatabaseAcces.databaseNameList.get(i);

            if (tempToMenageExecutedThread.compareAndSet(true, false)) {
                executeEvents = new ExecuteEvents();
                System.out.println("Run new process for database of name " + executeEvents.getName());
            } else {
                if (executeEvents != null) {
                    executeEvents.stopProcess();
                    System.out.println("Stop Process " + executeEvents.getName());
                }
                executeEvents = new ExecuteEvents();
            }
            executeEvents.setName(PublicDatabaseAcces.currentDatabaseName);
            executeEvents.start();
            startActivity(in);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wh_selector, container, false);
    }
}