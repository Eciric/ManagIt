package res.managit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }

    private boolean addCardsClosed(View view) {
        if (view.findViewById(R.id.addProductsCard).getVisibility() == View.VISIBLE)
            return false;
        if (view.findViewById(R.id.addWorkerCard).getVisibility() == View.VISIBLE)
            return false;
        if (view.findViewById(R.id.addSupplierCard).getVisibility() == View.VISIBLE)
            return false;
        if (view.findViewById(R.id.addCustomerCard).getVisibility() == View.VISIBLE)
            return false;

        return true;
    }

    public void setButtonListeners(@NonNull View view) {
        setMainButtonListeners(view);
        setAddButtonListeners(view);
        setCloseButtonListeners(view);
    }

    public void setMainButtonListeners(@NonNull View view) {
        Button products = view.findViewById(R.id.products);
        products.setOnClickListener((event) -> {
            if (addCardsClosed(view))
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new productsFragment()).commit();
        });

        Button workers = view.findViewById(R.id.workers);
        workers.setOnClickListener((event) -> {
            if (addCardsClosed(view))
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new workersFragment()).commit();
        });

        Button suppliers = view.findViewById(R.id.suppliers);
        suppliers.setOnClickListener((event) -> {
            if (addCardsClosed(view))
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new suppliersFragment()).commit();
        });

        Button customers = view.findViewById(R.id.customers);
        customers.setOnClickListener((event) -> {
            if (addCardsClosed(view))
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new customersFragment()).commit();
        });

        Button categories = view.findViewById(R.id.categories);
        categories.setOnClickListener((event) -> {
            if (addCardsClosed(view))
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new categoriesFragment()).commit();
        });
    }

    public void setAddButtonListeners(@NonNull View view) {
        Button addProduct = view.findViewById(R.id.addProducts);
        addProduct.setOnClickListener((event) -> {
            if (addCardsClosed(view)) {
                CardView card = view.findViewById(R.id.addProductsCard);
                card.setVisibility(View.VISIBLE);
            }
        });

        Button addWorker = view.findViewById(R.id.addWorker);
        addWorker.setOnClickListener((event) -> {
            if (addCardsClosed(view)) {
                CardView card = view.findViewById(R.id.addWorkerCard);
                card.setVisibility(View.VISIBLE);
            }
        });

        Button addSupplier = view.findViewById(R.id.addSupplier);
        addSupplier.setOnClickListener((event) -> {
            if (addCardsClosed(view)) {
                CardView card = view.findViewById(R.id.addSupplierCard);
                card.setVisibility(View.VISIBLE);
            }
        });

        Button addCustomer = view.findViewById(R.id.addCustomer);
        addCustomer.setOnClickListener((event) -> {
            if (addCardsClosed(view)) {
                CardView card = view.findViewById(R.id.addCustomerCard);
                card.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setCloseButtonListeners(@NonNull View view) {
        Button productClose = view.findViewById(R.id.productClose);
        productClose.setOnClickListener((e) -> {
            CardView card = view.findViewById(R.id.addProductsCard);
            card.setVisibility(View.INVISIBLE);
        });

        Button workerClose = view.findViewById(R.id.workerClose);
        workerClose.setOnClickListener((e) -> {
            CardView card = view.findViewById(R.id.addWorkerCard);
            card.setVisibility(View.INVISIBLE);
        });

        Button supplierClose = view.findViewById(R.id.supplierClose);
        supplierClose.setOnClickListener((e) -> {
            CardView card = view.findViewById(R.id.addSupplierCard);
            card.setVisibility(View.INVISIBLE);
        });

        Button customerClose = view.findViewById(R.id.customerClose);
        customerClose.setOnClickListener((e) -> {
            CardView card = view.findViewById(R.id.addCustomerCard);
            card.setVisibility(View.INVISIBLE);
        });
    }
}