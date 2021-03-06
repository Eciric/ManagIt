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
import android.widget.Button;

import java.util.Objects;

import static res.managit.dbo.DatabaseFunctions.saveDatabaseNames;

/**
 * Class which represents starting fragment with 3 selection buttons
 */
public class startingFragment extends Fragment implements View.OnClickListener {

    boolean exit;
    NavController navController;

    public startingFragment() {
    }

    public startingFragment(boolean exit) {
        this.exit = exit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_starting, container, false);
    }

    /**
     * Function used to initialize starting' fragment ui
     *
     * @param view               fragment's view
     * @param savedInstanceState fragment's bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (exit) requireActivity().finish();

        navController = Navigation.findNavController(view);
        Button createWH = view.findViewById(R.id.createWHButton);
        Button selectWH = view.findViewById(R.id.selectWHButton);
        Button quit = view.findViewById(R.id.quitButton);
        createWH.setOnClickListener(this);
        selectWH.setOnClickListener(this);
        quit.setOnClickListener(this);
    }

    /**
     * Function which select proper action after clicking on button - like creating db, selecting db or exit application
     *
     * @param v fragment's view
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.createWHButton) {
            navController.navigate(R.id.action_startingFragment_to_whCreatorFragment);
        } else if (v.getId() == R.id.selectWHButton) {
            navController.navigate(R.id.action_startingFragment_to_whSelectorFragment);
        } else if (v.getId() == R.id.quitButton) {
            saveDatabaseNames(getContext(), "DatabaseNames.csv");
            System.exit(0);
        }
    }
}