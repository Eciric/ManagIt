package res.managit.service;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;

/**
 * Class handling adding new customer
 */
public class AddCustomerHandler extends AsyncTask<Void, Void, String> {
    final String EMPTY_MSG = "Fields cannot be empty";
    final String NAME_INVALID_MSG = "Name can contain only letters";
    final String CITY_INVALID_MSG = "City can contain only letters";
    final String STREET_INVALID_MSG = "Street can contain only letters";
    final String COUNTRY_INVALID_MSG = "Country can contain only letters";
    final String NUMBER_INVALID_MSG = "Invalid street number";
    final String CODE_INVALID_MSG = "Invalid postal code (5 numbers)";
    final String PHONE_INVALID_MSG = "Invalid phone (9 numbers)";
    final String EXISTS_MSG = "Customer already exists";
    final String SUCCESS_MSG = "Customer successfully added";

    String customerName;
    String customerStreet;
    String customerStreetNumber;
    String customerCountry;
    String customerCity;
    String customerCityCode;
    String customerPhone;

    WarehouseDb db;
    View view;
    Context context;

    /**
     * Class constructor
     * @param context fragment's context
     * @param view fragment's view
     * @param db database on which operations will be done
     */
    public AddCustomerHandler(Context context, View view, WarehouseDb db) {
        this.db = db;
        this.view = view;
        this.context = context;
    }

    /**
     * Function to validate user input data.
     * After successful validation process of inserting a new customer is started.
     * @return validation message
     */
    @Override
    protected String doInBackground(Void... voids) {
        customerName = ((EditText)view.findViewById(R.id.customerName)).getText().toString();
        customerStreet = ((EditText)view.findViewById(R.id.customerStreet)).getText().toString();
        customerStreetNumber = ((EditText)view.findViewById(R.id.customerStreetNumber)).getText().toString();
        customerCountry = ((EditText)view.findViewById(R.id.customerCountry)).getText().toString();
        customerCity = ((EditText)view.findViewById(R.id.customerCity)).getText().toString();
        customerCityCode = ((EditText)view.findViewById(R.id.customerCityCode)).getText().toString();
        customerPhone = ((EditText)view.findViewById(R.id.customerPhone)).getText().toString();


        if (customerName.equals("") || customerStreet.equals("") || customerStreetNumber.equals("") ||
                customerCountry.equals("") || customerCity.equals("") || customerCityCode.equals("") || customerPhone.equals("") )
            return EMPTY_MSG;

        String validMsg = validateInput();
        if (validMsg != SUCCESS_MSG)
            return validMsg;

        List<Customer> customers = db.customerDao().getAll();
        List<String> customersNames = new ArrayList<>();
        for (Customer c : customers)
            customersNames.add(c.getName().toLowerCase());
        if (customersNames.contains(customerName.toLowerCase()))
            return EXISTS_MSG;

        new ContactInsertion().execute();

        return SUCCESS_MSG;
    }

    /**
     * Function to validate user input
     * @return validation message
     */
    private String validateInput() {
        if (!customerName.matches("[a-zA-Z]+"))
            return NAME_INVALID_MSG;
        if (!customerStreet.matches("[a-zA-Z ]+"))
            return STREET_INVALID_MSG;
        if (!customerCity.matches("[a-zA-Z ]+"))
            return CITY_INVALID_MSG;
        if (!customerCountry.matches("[a-zA-Z ]+"))
            return COUNTRY_INVALID_MSG;

        if (!customerStreetNumber.matches("[0-9]+"))
            return NUMBER_INVALID_MSG;
        if (!customerCityCode.matches("[0-9]+")  || customerCityCode.length() != 5)
            return CODE_INVALID_MSG;
        if (!customerPhone.matches("[0-9]+")  || customerPhone.length() != 9)
            return PHONE_INVALID_MSG;

        return SUCCESS_MSG;
    }

    /**
     * Function which updates ui based on passed result
     * @param result validation message from doInBackground method
     */
    @Override
    protected void onPostExecute(String result) {
        if (!result.equals(SUCCESS_MSG)) {
            TextView errorText = view.findViewById(R.id.customerError);
            errorText.setText(result);
        }
        else {
            ((EditText)view.findViewById(R.id.customerName)).setText("");
            ((EditText)view.findViewById(R.id.customerStreet)).setText("");
            ((EditText)view.findViewById(R.id.customerStreetNumber)).setText("");
            ((EditText)view.findViewById(R.id.customerCountry)).setText("");
            ((EditText)view.findViewById(R.id.customerCity)).setText("");
            ((EditText)view.findViewById(R.id.customerCityCode)).setText("");
            ((EditText)view.findViewById(R.id.customerPhone)).setText("");
            ((TextView)view.findViewById(R.id.customerError)).setText("");

            CardView cardView = view.findViewById(R.id.addCustomerCard);
            cardView.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(context, SUCCESS_MSG, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Inner class used to insert contact.
     * After contact insertion it invokes CustomerInsertion
     */
    public class ContactInsertion extends AsyncTask<Void, Void, Long> {
        Contact contact;

        /**
         * Function used to insert contact
         * @return id of inserted contact
         */
        @Override
        protected Long doInBackground(Void... voids) {
            contact = new Contact(customerStreet, Integer.parseInt(customerStreetNumber),
                    Integer.parseInt(customerCityCode), customerCity, customerCountry, customerPhone);
            return db.contactDao().insertContact(contact)[0];
        }

        /**
         * Function which invokes CustomInsertion
         * @param result id of inserted contact form doInBackground
         */
        @Override
        protected void onPostExecute(Long result) {
            new CustomerInsertion(result).execute();
        }

    }

    /**
     * Inner class used to insert customer
     */
    public class CustomerInsertion extends AsyncTask<Void, Void, Void> {
        long id;

        /**
         * Class constructor
         * @param id id of customer's contact
         */
        public CustomerInsertion(long id) {
            this.id = id;
        }

        /**
         * Function used to insert customer
         */
        @Override
        protected Void doInBackground(Void... voids) {
            db.customerDao().insertCustomer(new Customer(customerName, id));
            return null;
        }
    }
}
