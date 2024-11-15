package com.primerp.integradora.Cosas.Class;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "MyAppPrefs";
    private static final String KEY_AUTH_TOKEN = "authToken";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Guardar el token en SharedPreferences
    public void saveToken(String token) {
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.apply();
    }

    // Recuperar el token desde SharedPreferences
    public String getToken() {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null);
    }

    // Eliminar el token (Cerrar sesión)
    public void logout() {
        editor.remove(KEY_AUTH_TOKEN);
        editor.apply();
    }

    // Verificar si el usuario está autenticado
    public boolean isLoggedIn() {
        String token = getToken();
        return token != null && !token.isEmpty();
    }
}
