package res.managit.service;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;

public class CustomerRetriever extends AsyncTask<Void, Void, CustomerRetriever.Data> {
    WarehouseDb db;
    View view;
    Customer customer;

    /**
     * Inner class used to store customer data
     */
    protected class Data {
        protected Customer customer;
        protected Contact contact;

        public Data(Customer customer, Contact contact) {
            this.customer = customer;
            this.contact = contact;
        }
    }

    /**
     * Class constructor
     *
     * @param view     fragment's view
     * @param db       database on which operations will be done
     * @param customer customer which will be displayed
     */
    public CustomerRetriever(View view, WarehouseDb db, Customer customer) {
        this.db = db;
        this.view = view;
        this.customer = customer;
    }

    /**
     * Function to retrieve customer data from database
     *
     * @return customer data
     */
    @Override
    protected Data doInBackground(Void... voids) {
        Contact contact = db.contactDao().getById(customer.getContact_Id());
        Data data = new Data(customer, contact);
        return data;
    }

    /**
     * Function used to fill ui with customer data passed in result
     *
     * @param result customer data from doInBackground method
     */
    @Override
    protected void onPostExecute(Data result) {
        TextView name = view.findViewById(R.id.name);
        TextView street = view.findViewById(R.id.street);
        TextView city = view.findViewById(R.id.city);
        TextView country = view.findViewById(R.id.country);
        TextView phone = view.findViewById(R.id.phone);

        name.setText(result.customer.getName());
        street.setText(result.contact.getStreetNumber() + " " + result.contact.getStreetName() + " st");
        city.setText(result.contact.getPostalCode() + " " + result.contact.getCity());
        country.setText(result.contact.getCountry());
        phone.setText(result.contact.getPhoneNumber());
    }
}