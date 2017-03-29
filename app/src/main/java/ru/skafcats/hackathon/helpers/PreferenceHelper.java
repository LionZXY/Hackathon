package ru.skafcats.hackathon.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import ru.skafcats.hackathon.models.UserModel;

/**
 * Created by Nikita Kulikov on 29.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class PreferenceHelper {
    private SharedPreferences sharedPreferences = null;

    public PreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("PreferenceHelper", 0);
    }

    public String getLogin(String defaultVar) {
        return sharedPreferences.getString("login", defaultVar);
    }

    public String getString(String key, String defaultVar) {
        return sharedPreferences.getString(key, defaultVar);
    }

    public UserModel getUser() {
        UserModel toExit = new UserModel(getLogin(null));
        toExit.setName(getString("name", null))
                .setPhoneModel(getString("phoneModel", null))
                .setPhoneNumber(getString("phoneNumber", null))
                .setUsername(getString("surname", null));
        return toExit;
    }

    public boolean isLogin() {
        return sharedPreferences.getInt("isLogin", 0) == 1;
    }

    public void setUser(@Nullable UserModel user) {
        if (user == null) {
            sharedPreferences.edit().putInt("isLogin",0).apply();
            user = new UserModel("Not Logging");
        } else {
            sharedPreferences.edit().putInt("isLogin",1).apply();
        }

        sharedPreferences.edit().putString("login", user.getLogin())
                .putString("name", user.getName())
                .putString("phoneModel", user.getPhoneModel())
                .putString("phoneNumber", user.getPhone())
                .putString("surname", user.getName()).apply();
    }
}
