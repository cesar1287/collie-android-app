package comcesar1287.github.www.collie.controller.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private SharedPreferences sharedPref;

    private static final String TYPE_BLOCK = "_.TYPE_BLOCK";

    public SharedPref(Context context) {
        sharedPref = context.getSharedPreferences("MAIN_PREF", Context.MODE_PRIVATE);
    }

    /**
     * Preference for type block
     */
    public void setTypeBlock(String type) {
        sharedPref.edit().putString(TYPE_BLOCK, type).apply();
    }

    public String getTypeBlock() {
        return sharedPref.getString(TYPE_BLOCK, TYPE_BLOCK);
    }
}
