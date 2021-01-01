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
    long id;

    protected class Data {
        protected Supply supplier;
        protected Contact contact;

        public Data(Supply supplier, Contact contact) {
            this.supplier = supplier;
            this.contact = contact;
        }
    }

    public SupplierRetriever(View view, WarehouseDb db, long id) {
        this.db = db;
        this.view = view;
        this.id = id;
    }

    @Override
    protected Data doInBackground(Void... voids) {
        Supply supplier = db.supplyDao().getSupplyById(id);
        Contact contact = db.contactDao().getById(supplier.getContact_Id());
        Data data = new Data(supplier, contact);
        return data;
    }

    @Override
    protected void onPostExecute(Data result) {
        TextView name = view.findViewById(R.id.name);
        TextView street = view.findViewById(R.id.street);
        TextView city = view.findViewById(R.id.city);
        TextView country = view.findViewById(R.id.country);
        TextView phone = view.findViewById(R.id.phone);

        name.setText("Name: " + result.supplier.getName());
        street.setText("Street: " + result.contact.getStreetNumber() + " " + result.contact.getStreetName() + " st");
        city.setText("City: " + result.contact.getPostalCode() + " " + result.contact.getCity());
        country.setText("Country: " + result.contact.getCountry());
        phone.setText("Phone: " + result.contact.getPhoneNumber());
    }
}