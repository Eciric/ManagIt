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
import res.managit.service.ProductListRetriever;
import res.managit.service.ProductRetriever;
import res.managit.service.WorkerListRetriever;
import res.managit.service.WorkerRetriever;

public class workersFragment extends Fragment {
    public workersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new WorkerListRetriever(requireContext(), view, PublicDatabaseAcces.currentDatabase).execute();
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Workers");

        ListView productsList = view.findViewById(R.id.list);
        productsList.setOnItemClickListener((adapterView, view1, i, l) -> {
            View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.workers_popup, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            popupWindow.showAsDropDown(popupView, 0, 0);

            Button close = popupView.findViewById(R.id.close);
            close.setOnClickListener((event) -> {
                popupWindow.dismiss();
            });

            Long id = getWorkerId((String) adapterView.getAdapter().getItem(i));
            new WorkerRetriever(popupView, PublicDatabaseAcces.currentDatabase, id).execute();
        });
    }

    private long getWorkerId(String text) {
        int index = text.lastIndexOf(']');
        String id = text.substring(1, index);
        return Long.parseLong(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }
}