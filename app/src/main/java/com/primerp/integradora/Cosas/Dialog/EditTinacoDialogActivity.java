package com.primerp.integradora.Cosas.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.Modelos.User;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Cosas.Responst.TinacoRequest;
import com.primerp.integradora.Cosas.Responst.TinacoResponse;
import com.primerp.integradora.R;

import retrofit2.Call;

public class EditTinacoDialogActivity extends AppCompatActivity {
    private ApiService apiService;
    private SessionManager sessionManager;
    private EditText editName;
    private Button saveButton;
    private int tinacoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tinaco_dialog);
        editName = findViewById(R.id.edit_name);
        saveButton = findViewById(R.id.saveButton);
        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TINACO_ID")) {
            int tinacoId = intent.getIntExtra("TINACO_ID", -1);
            Log.d("TINACO_ID", "ID del Tinaco seleccionado desde edit: " + tinacoId);
            this.tinacoId = tinacoId;
        }
        loadTinacoData();

        ImageView backIcon = findViewById(R.id.iconback);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saveButton.setOnClickListener(v -> editProfile());

    }
    private void loadTinacoData() {
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;

        Call<TinacoResponse> call = apiService.getTinacoById(authToken, tinacoId);
        call.enqueue(new retrofit2.Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, retrofit2.Response<TinacoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Tinacos tinaco = response.body().getTinaco();

                    if (tinaco != null) {
                        Log.d("DEBUG", "Nombre del Tinaco: " + tinaco.getNombre());
                        editName.setText(tinaco.getNombre());
                    }
                } else {
                    Log.d("DEBUG", "Error en la respuesta de la API: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                Log.d("DEBUG", "Error al cargar los datos del tinaco: " + t.getMessage());
            }
        });
    }
    private void editProfile() {
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;

        String nombre = editName.getText().toString();
        TinacoRequest tinacoRequest = new TinacoRequest(nombre);

        Call<TinacoResponse> call = apiService.updateTinaco(authToken, tinacoId, tinacoRequest);
        call.enqueue(new retrofit2.Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, retrofit2.Response<TinacoResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("DEBUG", "Tinaco editado correctamente");
                    finish();
                } else {
                    Log.d("DEBUG", "Error al editar el tinaco: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                Log.d("DEBUG", "Error al editar el tinaco: " + t.getMessage());
            }
        });
    }

}