package com.primerp.integradora.ui.tinaco;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.TinacoRequest;
import com.primerp.integradora.Cosas.Responst.TinacoResponse;
import com.primerp.integradora.R;

import java.util.List;

import retrofit2.Call;

public class TinacoActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private ApiService apiService;
    private EditText nombrEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinaco);
        nombrEditText = findViewById(R.id.nombre);

        // Inicializar la sesion y el API
        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();
        // inicialisamos el boton register

        Button registerButton = findViewById(R.id.regsirerButton);
        //cancelButton
        Button cancelButton = findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(v -> finish());
        registerButton.setOnClickListener(v -> Tinacoadd());


    }
    public void Tinacoadd(){
        String nombre = nombrEditText.getText().toString().trim();
        String token = sessionManager.getToken();

        Log.d("DEBUG", "Token recuperado: " + token);

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token no válido", Toast.LENGTH_SHORT).show();
        }
        String authToken = "Bearer " + token;
        Log.d("DEBUG", "Token con prefijo Bearer: " + authToken);

        TinacoRequest tinacoRequest = new TinacoRequest(nombre);


        // Llamada a la API para obtener la lista de tinacos
        Call<TinacoResponse> call = apiService.addTinaco(authToken,tinacoRequest);
        call.enqueue(new retrofit2.Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, retrofit2.Response<TinacoResponse> response) {
                if (response.isSuccessful() && response.body()!= null) {
                    TinacoResponse tinacoResponse = response.body();
                    Toast.makeText(TinacoActivity.this, "Tinaco agregado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(TinacoActivity.this, "Error al agregar el tinaco", Toast.LENGTH_SHORT).show();
                    Log.e("API_RESPONSE", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                Toast.makeText(TinacoActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage(), t);
            }
        });

    }
}
