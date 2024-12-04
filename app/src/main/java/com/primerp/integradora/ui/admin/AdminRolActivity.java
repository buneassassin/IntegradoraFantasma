package com.primerp.integradora.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Responst.AdminResponse;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class AdminRolActivity extends AppCompatActivity {
    private ApiService apiService;
    private SessionManager sessionManager;
    private EditText emailEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rol);
        ImageView backIcon = findViewById(R.id.iconBack);
        Button changeRoleBut = findViewById(R.id.changeRoleBut);

        emailEdit = findViewById(R.id.emailEdit);

        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        changeRoleBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner roleSpinner = findViewById(R.id.roleSpinners);
                String selectedRole = roleSpinner.getSelectedItem().toString();

                if (selectedRole.equals("Seleccione un rol")) {
                    Toast.makeText(AdminRolActivity.this, "Por favor, seleccione un rol", Toast.LENGTH_SHORT).show();
                    return;
                }
                changeRole(selectedRole);


            }
        });
        loadroles();
    }
    private void loadroles() {
        String token = sessionManager.getToken();
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token no válido", Toast.LENGTH_SHORT).show();
            return;
        }
        String authToken = "Bearer " + token;
        Call<AdminResponse> call = apiService.getobtenerRol(authToken);
        call.enqueue(new retrofit2.Callback<AdminResponse>() {
            @Override
            public void onResponse(Call<AdminResponse> call, retrofit2.Response<AdminResponse> response) {
                if (response.isSuccessful()) {
                    AdminResponse adminResponse = response.body();
                    if (adminResponse != null && adminResponse.getRoles() != null) {
                        // Lista de roles obtenida de la API
                        List<String> rolesList = adminResponse.getRoles(); // Directamente usar la lista de roles

                        // Llenar el Spinner con los roles
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminRolActivity.this,
                                android.R.layout.simple_spinner_item, rolesList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Spinner roleSpinner = findViewById(R.id.roleSpinners);
                        roleSpinner.setAdapter(adapter);
                    } else {
                        Toast.makeText(AdminRolActivity.this, "No se encontraron roles", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdminRolActivity.this, "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdminResponse> call, Throwable t) {
                Toast.makeText(AdminRolActivity.this, "Error al cargar los roles", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void changeRole(String newRole) {
        // Obtener el email del usuario
        String email = emailEdit.getText().toString().trim();
        // Verificar si el email no es nulo o vacío
        if (email == null || email.isEmpty()) {
            Toast.makeText(AdminRolActivity.this, "Por favor, ingrese un email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el rol es válido
        if (newRole.equals("Seleccione un rol")) {
            Toast.makeText(AdminRolActivity.this, "Por favor, seleccione un rol", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un mapa para los datos que se van a enviar
        Map<String, String> roleData = new HashMap<>();
        roleData.put("email", email);
        roleData.put("rol", newRole);

        String token = sessionManager.getToken();
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token no válido", Toast.LENGTH_SHORT).show();
            return;
        }
        String authToken = "Bearer " + token;

        // Realizar la solicitud a la API para cambiar el rol
        Call<ApiResponse> call = apiService.postcambiarRol(authToken, roleData);
        call.enqueue(new retrofit2.Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(AdminRolActivity.this, "Rol cambiado correctamente.", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(AdminRolActivity.this, "Error al cambiar el rol.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(AdminRolActivity.this, "Error en la conexión.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}