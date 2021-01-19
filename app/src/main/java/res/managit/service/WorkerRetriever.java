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
    Worker worker;

    /**
     * Inner class which is a storage of worker data
     */
    protected class Data {
        protected Worker worker;
        protected Contact contact;

        public Data(Worker worker, Contact contact) {
            this.worker = worker;
            this.contact = contact;
        }
    }

    /**
     * Class constructor
     *
     * @param view   fragment's view
     * @param db     database on which operations will be done
     * @param worker worker which will be displayed
     */
    public WorkerRetriever(View view, WarehouseDb db, Worker worker) {
        this.db = db;
        this.view = view;
        this.worker = worker;
    }

    /**
     * Function to retrieve worker data from database
     *
     * @return worker data
     */
    @Override
    protected Data doInBackground(Void... voids) {
        Contact contact = db.contactDao().getById(worker.getContact_Id());
        Data data = new Data(worker, contact);
        return data;
    }

    /**
     * Function used to fill ui with worker data passed in result
     *
     * @param result worker data from doInBackground method
     */
    @Override
    protected void onPostExecute(Data result) {
        TextView fullname = view.findViewById(R.id.fullname);
        TextView role = view.findViewById(R.id.role);
        TextView street = view.findViewById(R.id.street);
        TextView city = view.findViewById(R.id.city);
        TextView country = view.findViewById(R.id.country);
        TextView phone = view.findViewById(R.id.phone);

        fullname.setText(result.worker.getName() + " " + result.worker.getLastName());
        role.setText(result.worker.getRole());
        street.setText(result.contact.getStreetNumber() + " " + result.contact.getStreetName() + " st");
        city.setText(result.contact.getPostalCode() + " " + result.contact.getCity());
        country.setText(result.contact.getCountry());
        phone.setText(result.contact.getPhoneNumber());
    }
}