package res.managit.service;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Supply;

public class SupplierRetriever extends AsyncTask<Void, Void, SupplierRetriever.Data> {
    WarehouseDb db;
    View view;
    Supply supplier;

    /**
     * Inner class which is a storage of supplier data
     */
    protected class Data {
        protected Supply supplier;
        protected Contact contact;

        public Data(Supply supplier, Contact contact) {
            this.supplier = supplier;
            this.contact = contact;
        }
    }

    /**
     * Class constructor
     * @param view fragment's view
     * @param db database on which operations will be done
     * @param supplier supplier which will be displayed
     */
    public SupplierRetriever(View view, WarehouseDb db, Supply supplier) {
        this.db = db;
        this.view = view;
        this.supplier = supplier;
    }

    /**
     * Function to retrieve supplier data from database
     * @return supplier data
     */
    @Override
    protected Data doInBackground(Void... voids) {
        Contact contact = db.contactDao().getById(supplier.getContact_Id());
        Data data = new Data(supplier, contact);
        return data;
    }

    /**
     * Function used to fill ui with supplier data passed in result
     * @param result supplier data from doInBackground method
     */
    @Override
    protected void onPostExecute(Data result) {
        TextView name = view.findViewById(R.id.name);
        TextView street = view.findViewById(R.id.street);
        TextView city = view.findViewById(R.id.city);
        TextView country = view.findViewById(R.id.country);
        TextView phone = view.findViewById(R.id.phone);

        name.setText(result.supplier.getName());
        street.setText(result.contact.getStreetNumber() + " " + result.contact.getStreetName() + " st");
        city.setText(result.contact.getPostalCode() + " " + result.contact.getCity());
        country.setText(result.contact.getCountry());
        phone.setText(result.contact.getPhoneNumber());
    }
}