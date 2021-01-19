package res.managit.service;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import res.managit.R;
import res.managit.dbo.WarehouseDb;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Worker;

/**
 * Class handling adding new worker
 */
public class AddWorkerHandler extends AsyncTask<Void, Void, String> {
    final String EMPTY_MSG = "Fields cannot be empty";
    final String NAME_INVALID_MSG = "Name can contain only letters";
    final String LASTNAME_INVALID_MSG = "Lastname can contain only letters";
    final String ROLE_INVALID_MSG = "Role can contain only letters";
    final String CITY_INVALID_MSG = "City can contain only letters";
    final String STREET_INVALID_MSG = "Street can contain only letters";
    final String COUNTRY_INVALID_MSG = "Country can contain only letters";
    final String NUMBER_INVALID_MSG = "Invalid street number";
    final String CODE_INVALID_MSG = "Invalid postal code (5 numbers)";
    final String PHONE_INVALID_MSG = "Invalid phone (9 numbers)";
    final String SUCCESS_MSG = "Worker successfully added";

    String workerName;
    String workerLastname;
    String workerRole;
    String workerStreet;
    String workerStreetNumber;
    String workerCountry;
    String workerCity;
    String workerCityCode;
    String workerPhone;

    WarehouseDb db;
    View view;
    Context context;

    /**
     * Class constructor
     *
     * @param context fragment's context
     * @param view    fragment's view
     * @param db      database on which operations will be done
     */
    public AddWorkerHandler(Context context, View view, WarehouseDb db) {
        this.db = db;
        this.view = view;
        this.context = context;
    }

    /**
     * Function to validate user input data.
     * After successful validation process of inserting a new worker is started.
     *
     * @return validation message
     */
    @Override
    protected String doInBackground(Void... voids) {
        workerName = ((EditText) view.findViewById(R.id.workerName)).getText().toString();
        workerLastname = ((EditText) view.findViewById(R.id.workerLastname)).getText().toString();
        workerRole = ((EditText) view.findViewById(R.id.workerRole)).getText().toString();
        workerStreet = ((EditText) view.findViewById(R.id.workerStreet)).getText().toString();
        workerStreetNumber = ((EditText) view.findViewById(R.id.workerStreetNumber)).getText().toString();
        workerCountry = ((EditText) view.findViewById(R.id.workerCountry)).getText().toString();
        workerCity = ((EditText) view.findViewById(R.id.workerCity)).getText().toString();
        workerCityCode = ((EditText) view.findViewById(R.id.workerCityCode)).getText().toString();
        workerPhone = ((EditText) view.findViewById(R.id.workerPhone)).getText().toString();


        if (workerName.equals("") || workerRole.equals("") || workerStreet.equals("") || workerStreetNumber.equals("") || workerLastname.equals("") ||
                workerCountry.equals("") || workerCity.equals("") || workerCityCode.equals("") || workerPhone.equals(""))
            return EMPTY_MSG;

        String validMsg = validateInput();
        if (validMsg != SUCCESS_MSG)
            return validMsg;

        new ContactInsertion().execute();

        return SUCCESS_MSG;
    }

    /**
     * Function to validate user input
     *
     * @return validation message
     */
    private String validateInput() {
        if (!workerName.matches("[a-zA-Z]+"))
            return NAME_INVALID_MSG;
        if (!workerLastname.matches("[a-zA-Z]+"))
            return LASTNAME_INVALID_MSG;
        if (!workerRole.matches("[a-zA-Z ]+"))
            return ROLE_INVALID_MSG;
        if (!workerStreet.matches("[a-zA-Z ]+"))
            return STREET_INVALID_MSG;
        if (!workerCity.matches("[a-zA-Z ]+"))
            return CITY_INVALID_MSG;
        if (!workerCountry.matches("[a-zA-Z ]+"))
            return COUNTRY_INVALID_MSG;

        if (!workerStreetNumber.matches("[0-9]+"))
            return NUMBER_INVALID_MSG;
        if (!workerCityCode.matches("[0-9]+") || workerCityCode.length() != 5)
            return CODE_INVALID_MSG;
        if (!workerPhone.matches("[0-9]+") || workerPhone.length() != 9)
            return PHONE_INVALID_MSG;

        return SUCCESS_MSG;
    }

    /**
     * Function which updates ui based on passed result
     *
     * @param result validation message from doInBackground method
     */
    @Override
    protected void onPostExecute(String result) {
        if (!result.equals(SUCCESS_MSG)) {
            TextView errorText = view.findViewById(R.id.workerError);
            errorText.setText(result);
        } else {
            ((EditText) view.findViewById(R.id.workerName)).setText("");
            ((EditText) view.findViewById(R.id.workerLastname)).setText("");
            ((EditText) view.findViewById(R.id.workerRole)).setText("");
            ((EditText) view.findViewById(R.id.workerStreet)).setText("");
            ((EditText) view.findViewById(R.id.workerStreetNumber)).setText("");
            ((EditText) view.findViewById(R.id.workerCountry)).setText("");
            ((EditText) view.findViewById(R.id.workerCity)).setText("");
            ((EditText) view.findViewById(R.id.workerCityCode)).setText("");
            ((EditText) view.findViewById(R.id.workerPhone)).setText("");
            ((TextView) view.findViewById(R.id.workerError)).setText("");

            CardView cardView = view.findViewById(R.id.addWorkerCard);
            cardView.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(context, SUCCESS_MSG, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Inner class used to insert contact.
     * After contact insertion it invokes WorkerInsertion
     */
    public class ContactInsertion extends AsyncTask<Void, Void, Long> {
        Contact contact;

        /**
         * Function used to insert contact
         *
         * @return id of inserted contact
         */
        @Override
        protected Long doInBackground(Void... voids) {
            contact = new Contact(workerStreet, Integer.parseInt(workerStreetNumber),
                    Integer.parseInt(workerCityCode), workerCity, workerCountry, workerPhone);
            return db.contactDao().insertContact(contact)[0];
        }

        /**
         * Function which invokes WorkerInsertion
         *
         * @param result id of inserted contact form doInBackground
         */
        @Override
        protected void onPostExecute(Long result) {
            new WorkerInsertion(result).execute();
        }

    }

    /**
     * Inner class used to insert worker
     */
    public class WorkerInsertion extends AsyncTask<Void, Void, Void> {
        long id;

        /**
         * Class constructor
         *
         * @param id id of worker's contact
         */
        public WorkerInsertion(long id) {
            this.id = id;
        }

        /**
         * Function used to insert worker
         */
        @Override
        protected Void doInBackground(Void... voids) {
            db.workerDao().insertWorker(new Worker(workerName, workerLastname, workerRole, id));
            return null;
        }
    }
}
