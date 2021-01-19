package res.managit.materials;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;

import androidx.core.content.ContextCompat;

import com.applandeo.materialcalendarview.CalendarUtils;

import res.managit.R;

/**
 * class handling adding icons in planner
 */
public final class DrawableUtils {

    /**
     * Function add circle with string inside to planner
     *
     * @param context variable which stores all data connected with android app
     * @param string  variable which will be inserted in icon
     * @return layer with circle with string inside
     */
    public static Drawable getCircleDrawableWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_circle);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 12);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }

    /**
     * Function add three dots  to planner
     *
     * @param context variable which stores all data connected with android app
     * @return layer with three dots
     */
    public static Drawable getThreeDots(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.sample_three_icons);

        //Add padding to too large icon
        return new InsetDrawable(drawable, 100, 0, 100, 0);
    }

    /**
     * No arguments class constructor
     */
    private DrawableUtils() {
    }
}
