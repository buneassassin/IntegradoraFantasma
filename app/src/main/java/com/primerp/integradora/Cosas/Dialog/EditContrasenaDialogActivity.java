package com.primerp.integradora.Cosas.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Cosas.Responst.PassaworRequest;
import com.primerp.integradora.R;
import com.primerp.integradora.ui.notifications.NotificationsFragment;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditContrasenaDialogActivity extends AppCompatActivity {
    private ApiService apiService;
    private SessionManager sessionManager;
    private EditText editcontra, confircontra;
    private Button saveButton, cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contrasena_dialog);
        editcontra = findViewById(R.id.edit_contra);
        confircontra = findViewById(R.id.confir_contra);

        saveButton = findViewById(R.id.saveButton);

        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();

        saveButton.setOnClickListener(v -> editcontrasena());

        ImageView backIcon = findViewById(R.id.iconback);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void editcontrasena() {
        String password = editcontra.getText().toString();
        String password_confirmation = confircontra.getText().toString();
        if (password.isEmpty() || password_confirmation.isEmpty()) {
            editcontra.setError("Por favor, ingrese una contraseña");
            confircontra.setError("Por favor, confirme la contraseña");
        }
        else if (!password.equals(password_confirmation)) {
            confircontra.setError("Las contraseñas no coinciden");
        }
        else {
            String token = sessionManager.getToken();

            Log.d("DEBUG", "Token recuperado: " + token);

            if (token == null || token.isEmpty()) {
                Toast.makeText(this, "Token no válido", Toast.LENGTH_SHORT).show();
                return;
            }

            String authToken = "Bearer " + token;
            Log.d("DEBUG", "Token con prefijo Bearer: " + authToken);


            PassaworRequest request  = new PassaworRequest(password,password_confirmation);
            Call<ApiResponse> call = apiService.updatePassword(authToken, request);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(EditContrasenaDialogActivity.this, "Contraseña actualizado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditContrasenaDialogActivity.this, "Error al actualizar el Contraseña", Toast.LENGTH_SHORT).show();
                        Log.e("API_RESPONSE", "Error: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(EditContrasenaDialogActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", t.getMessage(), t);
                }
            });


        }
    }
    public void closet() {
        Intent intent = new Intent(this, NotificationsFragment.class);
        startActivity(intent);
        finish();
    }
}