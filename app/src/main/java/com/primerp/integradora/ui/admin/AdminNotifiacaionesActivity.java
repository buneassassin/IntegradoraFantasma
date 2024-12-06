package com.primerp.integradora.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Responst.AdminResponse;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminNotifiacaionesActivity extends AppCompatActivity {

    private Spinner typeSpinner;
    private SessionManager sessionManager;
    private ApiService apiService;
    private EditText messageEdit,tituloEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notifiacaiones);
        ImageView backIcon = findViewById(R.id.iconBack);
        Button sendNotificationButton = findViewById(R.id.sendNotificationButton);

        // Inicializar elementos
        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();
        typeSpinner = findViewById(R.id.typeSpinner);
        messageEdit = findViewById(R.id.messageEdit);
        tituloEdit = findViewById(R.id.tituloEdit);


        loadTypes();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sendNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner roleSpinner = findViewById(R.id.typeSpinner);
                String type = roleSpinner.getSelectedItem().toString();

                if (type.equals("Seleccione un rol")) {
                    Toast.makeText(AdminNotifiacaionesActivity.this, "Por favor, seleccione un rol", Toast.LENGTH_SHORT).show();
                    return;
                }

                sendNotification(type);
            }
        });

    }

    private void loadTypes() {
        String token = sessionManager.getToken();

        // Verificar si el token es válido
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Agregar el token de autorización
        String authToken = "Bearer " + token;

        // Llamada a la API
        Call<AdminResponse> call = apiService.getgettype(authToken);
        call.enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> types = response.body().getTypes(); // Obtener los tipos
                    if (types != null && !types.isEmpty()) {
                        populateSpinner(types);
                    } else {
                        Toast.makeText(AdminNotifiacaionesActivity.this, "No se encontraron tipos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdminNotifiacaionesActivity.this, "Error al obtener los tipos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdminResponse> call, Throwable t) {
                Toast.makeText(AdminNotifiacaionesActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void populateSpinner(List<String> types) {
        // Adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                types
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    private void sendNotification(String type) {
        String token = sessionManager.getToken();

        // Verificar si el token es válido
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token no válido", Toast.LENGTH_SHORT).show();
            return;
        }
        String authToken = "Bearer " + token;

        String messag = messageEdit.getText().toString().trim();
        if (messag == null || messag.isEmpty()) {
            Toast.makeText(AdminNotifiacaionesActivity.this, "Por favor, ingrese un email", Toast.LENGTH_SHORT).show();
            return;
        }
        String title = tituloEdit.getText().toString().trim();
        if (title == null || title.isEmpty()) {
            Toast.makeText(AdminNotifiacaionesActivity.this, "Por favor, ingrese un email", Toast.LENGTH_SHORT).show();
            return;
        }
        // Crear un mapa para los datos que se van a enviar
        Map<String, String> messagData = new HashMap<>();
        messagData.put("title", title);
        messagData.put("mesaje", messag);
        messagData.put("type", type);

        Call<ApiResponse> call = apiService.postEnviarNotificacionesGeneral(authToken, messagData);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdminNotifiacaionesActivity.this, "Notificación enviada con éxito", Toast.LENGTH_SHORT).show();
                    messageEdit.setText("");
                    tituloEdit.setText("");
                } else {
                    Toast.makeText(AdminNotifiacaionesActivity.this, "Error al enviar la notificación", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(AdminNotifiacaionesActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
