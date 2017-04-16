package prerak.com.baldha.util;

import android.content.Context;
import android.content.SharedPreferences;

import prerak.com.baldha.R;

/**
 * Created by Bhadresh Chavada on 21-03-2017.
 */

public class SharedPreferences_baldaha {

    public static String USERID = "USERID";
    public static String shiftId = "ShiftId";
//    public static String

    public static void SaveValue(Context context, String Key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences("Shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Key, value);
        editor.commit();
    }

    public static String GetValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences("Shared", Context.MODE_PRIVATE);
        String Value = sharedPref.getString(key, "");
        return Value;
    }
}
