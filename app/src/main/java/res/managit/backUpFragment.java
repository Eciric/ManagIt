package res.managit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;



import static res.managit.dbo.DatabaseFunctions.downloadDatabaseBackUp;
import static res.managit.dbo.DatabaseFunctions.uploadDatabaseBackUp;

/**
 * Class which represents backUp fragment
 */
public class backUpFragment extends Fragment implements View.OnClickListener {

    FragmentManager fm;

    public backUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_backup,container,false);
    }

    /**
     * Function used to initialize backUp' fragment ui.
     * @param view fragment's view
     * @param savedInstanceState fragment's bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fm = requireActivity().getSupportFragmentManager();
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Backup");
        Button exportDb = view.findViewById(R.id.exportButton);
        Button importDb = view.findViewById(R.id.importButton);
        exportDb.setOnClickListener(this);
        importDb.setOnClickListener(this);
    }

    /**
     * Function which react after clicking on button - exports or imports database from Firebase
     * @param v fragment's view
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.exportButton) {
            uploadDatabaseBackUp();
            Toast.makeText(getContext(), "Database exported", Toast.LENGTH_SHORT).show();
            if (fm != null) {
                fm.beginTransaction().replace(R.id.fragment_container,
                        new planerFragment()).commit();
            }
        }
        else if (v.getId() == R.id.importButton) {
            downloadDatabaseBackUp();
            Toast.makeText(getContext(), "Database imported", Toast.LENGTH_SHORT).show();
            if (fm != null) {
                fm.beginTransaction().replace(R.id.fragment_container,
                        new planerFragment()).commit();
            }
        }

    }

}