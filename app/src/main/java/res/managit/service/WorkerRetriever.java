package res.managit.service;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Worker;

public class WorkerRetriever extends AsyncTask<Void, Void, WorkerRetriever.Data> {
    WarehouseDb db;
    View view;
    long id;

    protected class Data {
        protected Worker worker;
        protected Contact contact;

        public Data(Worker worker, Contact contact) {
            this.worker = worker;
            this.contact = contact;
        }
    }

    public WorkerRetriever(View view, WarehouseDb db, long id) {
        this.db = db;
        this.view = view;
        this.id = id;
    }

    @Override
    protected Data doInBackground(Void... voids) {
        Worker worker = db.workerDao().getWorkerById(id);
        Contact contact = db.contactDao().getById(worker.getContact_Id());
        Data data = new Data(worker, contact);
        return data;
    }

    @Override
    protected void onPostExecute(Data result) {
        TextView fullname = view.findViewById(R.id.fullname);
        TextView role = view.findViewById(R.id.role);
        TextView street = view.findViewById(R.id.street);
        TextView city = view.findViewById(R.id.city);
        TextView country = view.findViewById(R.id.country);
        TextView phone = view.findViewById(R.id.phone);

        fullname.setText("Fullname: " + result.worker.getName() + " " + result.worker.getLastName());
        role.setText("Role: " + result.worker.getRole());
        street.setText("Street: " + result.contact.getStreetNumber() + " " + result.contact.getStreetName() + " st");
        city.setText("City: " + result.contact.getPostalCode() + " " + result.contact.getCity());
        country.setText("Country: " + result.contact.getCountry());
        phone.setText("Phone: " + result.contact.getPhoneNumber());
    }
}