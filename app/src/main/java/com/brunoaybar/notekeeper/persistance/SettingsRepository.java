package com.brunoaybar.notekeeper.persistance;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsRepository{


    public void saveName(Context context, String name) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("KEY_NAME", name);
        editor.remove("KEY_NAME");
        editor.commit();
    }

    public String getSavedName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("KEY_NAME", null);
    }

    public void saveListStyleIndex(Context context, int indice) {

    }


    public int getListStyleIndex() {
        return 0;
    }


    //region Singleton

    private static SettingsRepository INSTANCE;
    private SettingsRepository(){}

    public static SettingsRepository getInstance(){
        if(INSTANCE == null){
            INSTANCE = new SettingsRepository();
        }
        return INSTANCE;
    }

    //endregion
}
