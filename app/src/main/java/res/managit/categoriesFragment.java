package res.managit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import res.managit.dbo.PublicDatabaseAcces;
import res.managit.service.CategoryListRetriever;

/**
 * Class which represents category fragment
 */
public class categoriesFragment extends Fragment {
    public categoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Function used to initialize categories' fragment ui.
     *
     * @param view               fragment's view
     * @param savedInstanceState fragment's bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new CategoryListRetriever(requireContext(), view, PublicDatabaseAcces.currentDatabase, getLayoutInflater()).execute();
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Categories");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }
}
