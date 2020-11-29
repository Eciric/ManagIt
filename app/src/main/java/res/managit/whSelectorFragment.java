package res.managit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class whSelectorFragment extends Fragment {
    ListView listView;
    ArrayList<String> dbNames;

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

        //NOTE(Przemek): Add database integration here, populate the dbNames with actual existing databases names.
        listView = view.findViewById(R.id.listview);
        dbNames = new ArrayList<>();
        dbNames.add("Warehouse1");
        dbNames.add("Warehouse2");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.listview_text_formatter, R.id.textView2, dbNames);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO(Przemek): I will expand this soon to another fragment with the selected database
                Toast.makeText(getContext(), "Selected: " + dbNames.get(i), Toast.LENGTH_SHORT).show();
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