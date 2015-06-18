package ua.pp.mmr.mymedicalrecord;

import android.app.Activity;
import android.content.res.Resources;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Клас для утилитных методов, которые могут понадобиться где угодно
 * Created by Uliana on 24.05.2015.
 */
public final class Utils {
    private final Activity activity;

    public static Utils instance;

    private Utils(Activity activity) {
        this.activity=activity;
    }

    public static Utils getInstance(Activity activity) {
        if (instance == null)
            instance = new Utils(activity);
        return instance;
    }

    protected void showMessage(String message, int type) {
        int messageShort;
        switch (type) {
            case 0:
                messageShort = Toast.LENGTH_SHORT;
                break;
            case 1:
                messageShort = Toast.LENGTH_LONG;
                break;
            default:
                messageShort = Toast.LENGTH_SHORT;
        }
        Toast.makeText(activity, message, messageShort).show();
    }

    protected void showMessage(String message) {
        showMessage(message, 0);
    }

    protected String getDate() {
        //String[] days = {""}
        Calendar c = Calendar.getInstance();
        StringBuffer result = new StringBuffer();
        result.append(c.get(Calendar.DAY_OF_MONTH));
        result.append(" ");
        result.append(c.getDisplayName(Calendar.MONTH, Calendar.LONG, activity.getResources().getConfiguration().locale));
        result.append(" ");
        result.append(c.get(Calendar.YEAR));
        result.append(" ");
        result.append(c.get(Calendar.HOUR_OF_DAY));
        result.append(":");
        result.append(c.get(Calendar.MINUTE));
        return result.toString();
    }

    protected int getDateInstance(int instance) {
        Calendar c = Calendar.getInstance();
        return c.get(instance);
    }

    protected int getDayDegrees() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY)*15;
    }

    protected String getPartOfDay() {
        Calendar c = Calendar.getInstance();
        switch (c.get(Calendar.HOUR_OF_DAY)){
            case 23:
            case 24:
            case 0:
            case 1:
            case 2:
            case 3:
                return getStringFromResources(R.string.night);
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                return getStringFromResources(R.string.morning);
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                return getStringFromResources(R.string.day);
            default:
                return getStringFromResources(R.string.evning);
        }
    }

    protected Resources getResources() {
        return activity.getResources();
    }

    protected String getStringFromResources(int string) {
        return this.getResources().getString(string);
    }

    protected Activity getActivity(){
        return activity;
    }

    protected Date getDateFromString(String dateFormat) {
                DateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        try {
            return format.parse(dateFormat);
        } catch (ParseException e) {
            return null;
        }
    }

}
