package res.managit.wizard.event;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import res.managit.AddEventFirstStepActivity;

/**
 * fragment responsible for choice date
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    /**
     * @param savedInstanceState
     * @return new instance of DatePickerDialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * @param view current view
     * @param year year to pass to creator event
     * @param month month to pass to creator event
     * @param day day to pass to creator event
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        AddEventFirstStepActivity.setYearEvent(year);
        AddEventFirstStepActivity.setMonthEvent(month);
        AddEventFirstStepActivity.setDayEvent(day);
    }
}