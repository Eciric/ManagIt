package res.managit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Product;
import res.managit.dbo.helpers.ExecuteEvents;

public class whSelectorFragment extends Fragment {
    ListView listView;
    ArrayList<String> dbNames;
    NavController navController;
    int temp = 0;
    public static boolean run = true;

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if ( PublicDatabaseAcces.currentDatabase == null ){
                    System.err.println("jest nulem!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    temp = 1;
                }
                Intent in = new Intent(getActivity(), MenuActivity.class);
                in.putExtra("dbName", PublicDatabaseAcces.databaseNameList.get(i));
                PublicDatabaseAcces.currentDatabase = PublicDatabaseAcces.getDatabaseByName(PublicDatabaseAcces.databaseNameList.get(i));
                PublicDatabaseAcces.currentDatabaseName = PublicDatabaseAcces.databaseNameList.get(i);

                Executors.newSingleThreadExecutor().execute(() -> {
                    PublicDatabaseAcces.currentDatabaseEventNumber = PublicDatabaseAcces.currentDatabase.eventDao().getAll().size();
                });
                if ( temp == 1 ){
                    executeEvents = new ExecuteEvents();
                    temp = 0;
                }else {
                    executeEvents.stopProcess();
                    executeEvents.interrupt();
                    System.out.println("konczy proces");
                    executeEvents = new ExecuteEvents();
                }
                executeEvents.setName(PublicDatabaseAcces.currentDatabaseName);
                executeEvents.start();
                startActivity(in);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wh_selector, container, false);
    }
}