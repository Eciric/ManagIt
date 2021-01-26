package res.managit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Class which represents scanner Fragment
 */
public class scannerFragment extends Fragment {


    TextView textBarcode;
    Button btnScanBarCode;

    public scannerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Function which is responsible for set all fragment UI.
     * It also set lisener to scanner button
     *
     * @param inflater           object that can be used to inflate any views in the fragment
     * @param container          this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState fragment's bundle
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scaner, container, false);

        textBarcode = view.findViewById(R.id.barCode);
        btnScanBarCode = view.findViewById(R.id.btnScanBarCode);

        btnScanBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanner();
            }
        });

        return view;
    }

    /**
     * Function which is responsible for run bar code scanner API
     */
    public void scanner() {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(scannerFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("Scan barcode");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    /**
     * Function which return bar code and data connected with that number
     * @param requestCode number of bar code
     * @param resultCode number of bar code
     * @param data data connected with bar code
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "Cancel scan", Toast.LENGTH_SHORT).show();
            } else {
                textBarcode.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}