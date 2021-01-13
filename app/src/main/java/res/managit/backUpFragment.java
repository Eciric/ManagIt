package res.managit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;



import static res.managit.dbo.DatabaseFunctions.downloadDatabaseBackUp;
import static res.managit.dbo.DatabaseFunctions.uploadDatabaseBackUp;

public class backUpFragment extends Fragment implements View.OnClickListener {


    public backUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_backup,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button exportDb = view.findViewById(R.id.exportButton);
        Button importDb = view.findViewById(R.id.importButton);
        exportDb.setOnClickListener(this);
        importDb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.exportButton) {
            uploadDatabaseBackUp();
            Toast.makeText(getContext(), "Database exported", Toast.LENGTH_SHORT).show();
        }
        else if (v.getId() == R.id.importButton) {
            downloadDatabaseBackUp();
            Toast.makeText(getContext(), "Database imported", Toast.LENGTH_SHORT).show();
        }

    }

}