package com.primerp.integradora.Cosas.Class;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Guarda el token
    public void saveToken(String token) {
        Log.d("DEBUG", "Guardando token: " + token);
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.apply();
    }

    // Recupera el token
    public String getToken() {
        String token = sharedPreferences.getString(KEY_AUTH_TOKEN, null);
        Log.d("DEBUG", "Recuperando token en getToken(): " + token);
        return token;
    }

    // Elimina el token
    public void clearToken() {
        Log.d("DEBUG", "Eliminando token");
        editor.remove(KEY_AUTH_TOKEN);
        editor.apply();
    }
}
