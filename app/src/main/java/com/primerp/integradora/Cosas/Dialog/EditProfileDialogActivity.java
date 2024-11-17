package com.primerp.integradora.Cosas.Dialog;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Class.User;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.R;
import com.primerp.integradora.ui.notifications.NotificationsFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileDialogActivity extends AppCompatActivity {
    private ApiService apiService;
    private SessionManager sessionManager;
    private EditText editName, editEmail, editPhone, editUser;
    private Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_dialog_fragment);

        // Inicializar campos de entrada
        editUser = findViewById(R.id.edit_user);
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editPhone = findViewById(R.id.edit_phone);

        // Inicializar botones
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();

        loadUserData();

        // Lógica para el botón Guardar
        saveButton.setOnClickListener(v -> editProfile());

        cancelButton.setOnClickListener(v -> close());
    }

    private void editProfile() {
        String user = editUser.getText().toString();
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String phone = editPhone.getText().toString();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || user.isEmpty()) {
            Toast.makeText(EditProfileDialogActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String token = sessionManager.getToken();

        Log.d("DEBUG", "Token recuperado: " + token);

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        String authToken = "Bearer " + token;
        Log.d("DEBUG", "Token con prefijo Bearer: " + authToken);

        // Crear objeto RegisterRequest
        RegisterRequest registerRequest = new RegisterRequest(user, name, email, phone);

        // Llamar a la API
        Call<ApiResponse> call = apiService.updateUser(authToken, registerRequest);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditProfileDialogActivity.this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditProfileDialogActivity.this, "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
                    Log.e("API_RESPONSE", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(EditProfileDialogActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage(), t);
            }
        });
    }

    public void close() {
        Intent intent = new Intent(this, NotificationsFragment.class);
        startActivity(intent);
        finish();
    }
    private void loadUserData() {
        String token = sessionManager.getToken();

        String authToken = "Bearer " + token;
        Log.d("DEBUG", "Token con prefijo Bearer: " + authToken);
        Call<ApiResponse> call = apiService.getMe(token);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                User user = response.body().getUser();
                if (response.isSuccessful() && response.body() != null) {
                    editUser.setText(user.getUsuarioNom());
                    editName.setText(user.getPersona().getNombres());
                    editEmail.setText(user.getEmail());
                    editPhone.setText(user.getPersona().getTelefono());
                } else {
                    Toast.makeText(EditProfileDialogActivity.this, "Error al cargar los datos del usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(EditProfileDialogActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage(), t);
            }
        });
    }

}
