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
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.service.AddCustomerHandler;
import res.managit.service.AddProductHandler;
import res.managit.service.AddSupplierHandler;
import res.managit.service.AddWorkerHandler;
import res.managit.service.ProductSpinnerInitializer;


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
        initCategorySpinner(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }

    private boolean addCardsClosed(@NonNull View view) {
        if (view.findViewById(R.id.addProductsCard).getVisibility() == View.VISIBLE)
            return false;
        if (view.findViewById(R.id.addWorkerCard).getVisibility() == View.VISIBLE)
            return false;
        if (view.findViewById(R.id.addSupplierCard).getVisibility() == View.VISIBLE)
            return false;
        if (view.findViewById(R.id.addCustomerCard).getVisibility() == View.VISIBLE)
            return false;
        if (view.findViewById(R.id.addCategoryCard).getVisibility() == View.VISIBLE)
            return false;

        return true;
    }

    public void setButtonListeners(@NonNull View view) {
        setMainButtonListeners(view);
        setAddButtonListeners(view);
        setSubAddButtonListeners(view);
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

        Button addCategory = view.findViewById(R.id.addCategory);
        addCategory.setOnClickListener((event) -> {
            if (addCardsClosed(view)) {
                CardView card = view.findViewById(R.id.addCategoryCard);
                card.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setSubAddButtonListeners(@NonNull View view) {
        Button productAdd = view.findViewById(R.id.productAdd);
        productAdd.setOnClickListener((event) -> {
            new AddProductHandler(getContext(), view, PublicDatabaseAcces.currentDatabase).execute();
        });

        Button workerAdd = view.findViewById(R.id.workerAdd);
        workerAdd.setOnClickListener((event) -> {
            new AddWorkerHandler(getContext(), view, PublicDatabaseAcces.currentDatabase).execute();
        });

        Button supplierAdd = view.findViewById(R.id.supplierAdd);
        supplierAdd.setOnClickListener((event) -> {
            new AddSupplierHandler(getContext(), view, PublicDatabaseAcces.currentDatabase).execute();
        });

        Button customerAdd = view.findViewById(R.id.customerAdd);
        customerAdd.setOnClickListener((event) -> {
            new AddCustomerHandler(getContext(), view, PublicDatabaseAcces.currentDatabase).execute();
        });
    }

    public void setCloseButtonListeners(@NonNull View view) {
        Button productClose = view.findViewById(R.id.productClose);
        productClose.setOnClickListener((e) -> {
            CardView card = view.findViewById(R.id.addProductsCard);
            card.setVisibility(View.INVISIBLE);

            ((TextView)view.findViewById(R.id.productError)).setText("");
            ((EditText)view.findViewById(R.id.productName)).setText("");
        });

        Button workerClose = view.findViewById(R.id.workerClose);
        workerClose.setOnClickListener((e) -> {
            CardView card = view.findViewById(R.id.addWorkerCard);
            card.setVisibility(View.INVISIBLE);

            ((EditText)view.findViewById(R.id.workerName)).setText("");
            ((EditText)view.findViewById(R.id.workerLastname)).setText("");
            ((EditText)view.findViewById(R.id.workerRole)).setText("");
            ((EditText)view.findViewById(R.id.workerStreet)).setText("");
            ((EditText)view.findViewById(R.id.workerStreetNumber)).setText("");
            ((EditText)view.findViewById(R.id.workerCountry)).setText("");
            ((EditText)view.findViewById(R.id.workerCity)).setText("");
            ((EditText)view.findViewById(R.id.workerCityCode)).setText("");
            ((EditText)view.findViewById(R.id.workerPhone)).setText("");
            ((TextView)view.findViewById(R.id.workerError)).setText("");
        });

        Button supplierClose = view.findViewById(R.id.supplierClose);
        supplierClose.setOnClickListener((e) -> {
            CardView card = view.findViewById(R.id.addSupplierCard);
            card.setVisibility(View.INVISIBLE);

            ((EditText)view.findViewById(R.id.supplierName)).setText("");
            ((EditText)view.findViewById(R.id.supplierStreet)).setText("");
            ((EditText)view.findViewById(R.id.supplierStreetNumber)).setText("");
            ((EditText)view.findViewById(R.id.supplierCountry)).setText("");
            ((EditText)view.findViewById(R.id.supplierCity)).setText("");
            ((EditText)view.findViewById(R.id.supplierCityCode)).setText("");
            ((EditText)view.findViewById(R.id.supplierPhone)).setText("");
            ((TextView)view.findViewById(R.id.supplierError)).setText("");
        });

        Button customerClose = view.findViewById(R.id.customerClose);
        customerClose.setOnClickListener((e) -> {
            CardView card = view.findViewById(R.id.addCustomerCard);
            card.setVisibility(View.INVISIBLE);

            ((EditText)view.findViewById(R.id.customerName)).setText("");
            ((EditText)view.findViewById(R.id.customerStreet)).setText("");
            ((EditText)view.findViewById(R.id.customerStreetNumber)).setText("");
            ((EditText)view.findViewById(R.id.customerCountry)).setText("");
            ((EditText)view.findViewById(R.id.customerCity)).setText("");
            ((EditText)view.findViewById(R.id.customerCityCode)).setText("");
            ((EditText)view.findViewById(R.id.customerPhone)).setText("");
            ((TextView)view.findViewById(R.id.customerError)).setText("");
        });

        Button categoryClose = view.findViewById(R.id.categoryClose);
        categoryClose.setOnClickListener((e) -> {
            CardView card = view.findViewById(R.id.addCategoryCard);
            card.setVisibility(View.INVISIBLE);
        });
    }


    public void initCategorySpinner(@NonNull View view) {
        new ProductSpinnerInitializer(getContext(), view, PublicDatabaseAcces.currentDatabase).execute();
    }
}