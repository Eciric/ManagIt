package res.managit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Product;


import java.util.concurrent.Executors;

import static res.managit.dbo.DatabaseFunctions.createDatabase;

public class whCreatorFragment extends Fragment implements View.OnClickListener {

    NavController navController;
    public whCreatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wh_creator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        Button create = view.findViewById(R.id.createButton);

        create.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createButton) {
            //NOTE(Przemek): Implement the database integration here.
            EditText text = (EditText) getView().findViewById(R.id.et_name);
            String name = text.getText().toString();
            TextView errorText = getView().findViewById(R.id.error_text);

            if (name.isEmpty()) {
                errorText.setText("Warehouse name cannot be empty!");
                return;
            }

            //Executors.newSingleThreadExecutor().execute(() -> {});

            WarehouseDb db = createDatabase(getContext(),name);
            if (db != null){
                errorText.setText("");
                navController.navigate(R.id.action_whCreatorFragment_to_whSelectorFragment);
            }
            else {
                errorText.setText("This name is already in use!");
            }

            text.getText().clear();
        }
    }
}