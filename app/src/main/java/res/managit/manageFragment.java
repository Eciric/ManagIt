package res.managit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

public class manageFragment extends Fragment {
    public manageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Manage");
        setButtonListeners(view);
    }

    public void setButtonListeners(@NonNull View view) {
        Button products = view.findViewById(R.id.products);
        products.setOnClickListener((event) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new productsFragment()).commit();
        });

        Button workers = view.findViewById(R.id.workers);
        workers.setOnClickListener((event) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new workersFragment()).commit();
        });

        Button suppliers = view.findViewById(R.id.suppliers);
        suppliers.setOnClickListener((event) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new suppliersFragment()).commit();
        });

        Button customers = view.findViewById(R.id.customers);
        customers.setOnClickListener((event) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new customersFragment()).commit();
        });

        Button categories = view.findViewById(R.id.categories);
        categories.setOnClickListener((event) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new categoriesFragment()).commit();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }
}