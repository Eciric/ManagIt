package res.managit.service;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;

public class AddSupplierHandler extends AsyncTask<Void, Void, String> {
    final String EMPTY_MSG = "Fields cannot be empty";
    final String NAME_INVALID_MSG = "Name can contain only letters";
    final String CITY_INVALID_MSG = "City can contain only letters";
    final String STREET_INVALID_MSG = "Street can contain only letters";
    final String COUNTRY_INVALID_MSG = "Country can contain only letters";
    final String NUMBER_INVALID_MSG = "Invalid street number";
    final String CODE_INVALID_MSG = "Invalid postal code (5 numbers)";
    final String PHONE_INVALID_MSG = "Invalid phone (9 numbers)";
    final String EXISTS_MSG = "Supplier already exists";
    final String SUCCESS_MSG = "Supplier successfully added";

    String supplierName;
    String supplierStreet;
    String supplierStreetNumber;
    String supplierCountry;
    String supplierCity;
    String supplierCityCode;
    String supplierPhone;

    WarehouseDb db;
    View view;
    Context context;

    public AddSupplierHandler(Context context, View view, WarehouseDb db) {
        this.db = db;
        this.view = view;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        supplierName = ((EditText)view.findViewById(R.id.supplierName)).getText().toString();
        supplierStreet = ((EditText)view.findViewById(R.id.supplierStreet)).getText().toString();
        supplierStreetNumber = ((EditText)view.findViewById(R.id.supplierStreetNumber)).getText().toString();
        supplierCountry = ((EditText)view.findViewById(R.id.supplierCountry)).getText().toString();
        supplierCity = ((EditText)view.findViewById(R.id.supplierCity)).getText().toString();
        supplierCityCode = ((EditText)view.findViewById(R.id.supplierCityCode)).getText().toString();
        supplierPhone = ((EditText)view.findViewById(R.id.supplierPhone)).getText().toString();


        if (supplierName.equals("") || supplierStreet.equals("") || supplierStreetNumber.equals("") ||
                supplierCountry.equals("") || supplierCity.equals("") || supplierCityCode.equals("") || supplierPhone.equals("") )
            return EMPTY_MSG;

        String validMsg = validateInput();
        if (validMsg != SUCCESS_MSG)
            return validMsg;

        List<Supply> suppliers = db.supplyDao().getAll();
        List<String> suppliersNames = new ArrayList<>();
        for (Supply s : suppliers)
            suppliersNames.add(s.getName().toLowerCase());
        if (suppliersNames.contains(supplierName.toLowerCase()))
            return EXISTS_MSG;

        new ContactInsertion().execute();

        return SUCCESS_MSG;
    }

    private String validateInput() {
        if (!supplierName.matches("[a-zA-Z]+"))
            return NAME_INVALID_MSG;
        if (!supplierStreet.matches("[a-zA-Z ]+"))
            return STREET_INVALID_MSG;
        if (!supplierCity.matches("[a-zA-Z ]+"))
            return CITY_INVALID_MSG;
        if (!supplierCountry.matches("[a-zA-Z ]+"))
            return COUNTRY_INVALID_MSG;

        if (!supplierStreetNumber.matches("[0-9]+"))
            return NUMBER_INVALID_MSG;
        if (!supplierCityCode.matches("[0-9]+")  || supplierCityCode.length() != 5)
            return CODE_INVALID_MSG;
        if (!supplierPhone.matches("[0-9]+")  || supplierPhone.length() != 9)
            return PHONE_INVALID_MSG;

        return SUCCESS_MSG;
    }

    @Override
    protected void onPostExecute(String result) {
        if (!result.equals(SUCCESS_MSG)) {
            TextView errorText = view.findViewById(R.id.supplierError);
            errorText.setText(result);
        }
        else {
            ((EditText)view.findViewById(R.id.supplierName)).setText("");
            ((EditText)view.findViewById(R.id.supplierStreet)).setText("");
            ((EditText)view.findViewById(R.id.supplierStreetNumber)).setText("");
            ((EditText)view.findViewById(R.id.supplierCountry)).setText("");
            ((EditText)view.findViewById(R.id.supplierCity)).setText("");
            ((EditText)view.findViewById(R.id.supplierCityCode)).setText("");
            ((EditText)view.findViewById(R.id.supplierPhone)).setText("");
            ((TextView)view.findViewById(R.id.supplierError)).setText("");

            CardView cardView = view.findViewById(R.id.addSupplierCard);
            cardView.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(context, SUCCESS_MSG, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public class ContactInsertion extends AsyncTask<Void, Void, Long> {
        Contact contact;

        @Override
        protected Long doInBackground(Void... voids) {
            contact = new Contact(supplierStreet, Integer.parseInt(supplierStreetNumber),
                    Integer.parseInt(supplierCityCode), supplierCity, supplierCountry, supplierPhone);
            return db.contactDao().insertContact(contact)[0];
        }
        @Override
        protected void onPostExecute(Long result) {
            new SupplierInsertion(result).execute();
        }

    }

    public class SupplierInsertion extends AsyncTask<Void, Void, Void> {
        long id;

        public SupplierInsertion(long id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.supplyDao().insertSupply(new Supply(supplierName, id));
            return null;
        }
    }
}
