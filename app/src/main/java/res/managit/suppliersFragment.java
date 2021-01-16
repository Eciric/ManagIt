package res.managit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.dbo.entity.Supply;
import res.managit.service.SupplierRetriever;
import res.managit.service.SuppliersListRetriever;

/**
 * Class which represents supplier fragment
 */
public class suppliersFragment extends Fragment {
    public suppliersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Function used to initialize suppliers' fragment ui.
     * @param view fragment's view
     * @param savedInstanceState fragment's bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new SuppliersListRetriever(requireContext(), view, PublicDatabaseAcces.currentDatabase, getLayoutInflater()).execute();
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Suppliers");

        ListView productsList = view.findViewById(R.id.list);
        productsList.setOnItemClickListener((adapterView, view1, i, l) -> {
            View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.suppliers_customers_popup, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            popupWindow.showAsDropDown(popupView, 0, 0);

            Button close = popupView.findViewById(R.id.close);
            close.setOnClickListener((event) -> {
                popupWindow.dismiss();
            });

            new SupplierRetriever(popupView, PublicDatabaseAcces.currentDatabase, (Supply)adapterView.getAdapter().getItem(i)).execute();
        });
    }

    private long getSupplierId(String text) {
        int index = text.lastIndexOf(']');
        String id = text.substring(1,index);
        return Long.parseLong(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }
}