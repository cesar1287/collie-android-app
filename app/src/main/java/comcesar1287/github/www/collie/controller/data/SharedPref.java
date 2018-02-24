package comcesar1287.github.www.collie.controller.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private SharedPreferences sharedPref;

    private static final String TYPE_BLOCK = "_.TYPE_BLOCK";
    private static final String FIRST_EXECUTE = "_.FIRST_EXECUTE";
    private static final String FATHER = "father";
    private static final String EMAIL_FATHER = "email_child";
    private static final String NAME_CHILD = "name_child";
    private static final String AGE_CHILD = "age_child";
    public SharedPreferences.Editor edit;

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

    public void setNameFather(String name){
        sharedPref.edit().putString(FATHER, name).apply();
    }

    public String getNameFather() {
        return sharedPref.getString(FATHER, FATHER);
    }

    public void setEmailFather(String email){
        sharedPref.edit().putString(EMAIL_FATHER, email).apply();
    }

    public String getEmailFather() {
        return sharedPref.getString(EMAIL_FATHER, EMAIL_FATHER);
    }

    public void setNameChild(String nameChild){
        sharedPref.edit().putString(NAME_CHILD, nameChild).apply();
    }

    public String getNameChild() {
        return sharedPref.getString(NAME_CHILD, NAME_CHILD);
    }

    public void setAgeChild(String ageChild){
        sharedPref.edit().putString(AGE_CHILD, ageChild).apply();
    }

    public String getAgeChild() {
        return sharedPref.getString(AGE_CHILD, AGE_CHILD);
    }
}
