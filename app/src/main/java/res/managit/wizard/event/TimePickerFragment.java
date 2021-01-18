package res.managit.wizard.event;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import res.managit.AddEventFirstStepActivity;

/**
 * fragment responsible for choice time
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    /**
     * @param savedInstanceState
     * @return new instance of TimePickerDialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    /**
     * @param view current view
     * @param hourOfDay hour pass to creator event
     * @param minute minute pass to creator event
     */
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        AddEventFirstStepActivity.setHourOfDayEvent(hourOfDay);
        AddEventFirstStepActivity.setMinuteEvent(minute);
    }
}
