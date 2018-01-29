package comcesar1287.github.www.collie.controller.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private SharedPreferences sharedPref;

    private static final String TYPE_BLOCK = "_.TYPE_BLOCK";
    private static final String FIRST_EXECUTE = "_.FIRST_EXECUTE";

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

    public boolean isFirstExecute(){
        return sharedPref.getBoolean(FIRST_EXECUTE, true);
    }

    public void setFirstExecute(){
        sharedPref.edit().putBoolean(FIRST_EXECUTE, false).apply();
    }
}
