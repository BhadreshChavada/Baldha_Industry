package prerak.com.baldha.util;

import android.content.Context;
import android.content.SharedPreferences;

import prerak.com.baldha.R;

/**
 * Created by Bhadresh Chavada on 21-03-2017.
 */

public class SharedPreferences_baldaha {

    public static String USERID = "";

    public static void SaveValue(Context context, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences("Shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USERID, value);
        editor.commit();
    }

    public static String GetValue(Context context, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences("Shared", Context.MODE_PRIVATE);
        String Value = sharedPref.getString(value, "");
        return Value;
    }
}
