package com.primerp.integradora.ui.tinacoDetalle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Dialog.EditTinacoDialogActivity;
import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.Responst.TinacoResponse;
import com.primerp.integradora.R;
import com.primerp.integradora.databinding.ActivityTinacoGraficaBinding;
import com.primerp.integradora.ui.tinacoGrafica.TinacoGraficaActivity;

import retrofit2.Call;

public class TinacoDetalleActivity extends AppCompatActivity {
    private ApiService apiService;
    private SessionManager sessionManager;
    private TextView textTitulo,nombretinaco;
    private Button btndelet;
    private int tinacoId;
    private String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinaco_detalle);
        CardView cardEditar = findViewById(R.id.cardEditar);
        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();
        textTitulo = findViewById(R.id.textTituloTinaco);
        nombretinaco = findViewById(R.id.nombretinaco);
        btndelet= findViewById(R.id.btn_delet);
        ImageView backIcon = findViewById(R.id.iconback);
        ImageView irGarfica = findViewById(R.id.irGarfica);

        Intent intents = getIntent();
        if (intents != null && intents.hasExtra("TINACO_ID")) {
            int tinacoId = intents.getIntExtra("TINACO_ID", -1);
            Log.d("TINACO_ID", "ID del Tinaco seleccionado: " + tinacoId);
            this.tinacoId = tinacoId;
        }
        cardEditar.setOnClickListener(v -> {
            Intent intentedit = new Intent(TinacoDetalleActivity.this, EditTinacoDialogActivity.class);

            intentedit.putExtra("TINACO_ID", this.tinacoId);
            Log.d("TINACO_ID", "ID del Tinaco seleccionado: " + this.tinacoId);

            startActivity(intentedit);
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        irGarfica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TinacoDetalleActivity.this, TinacoGraficaActivity.class);
                intent.putExtra("tinaco_title", nombre);
                startActivity(intent);
            }
        });
        btndelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delettinaco();
            }
        });
        loadTinacoData();
    }
    private void loadTinacoData(){
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;

        Call<TinacoResponse> call = apiService.getTinacoById(authToken, tinacoId);
        call.enqueue(new retrofit2.Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, retrofit2.Response<TinacoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Tinacos tinaco = response.body().getTinaco();

                    if (tinaco != null) {
                        textTitulo.setText(tinaco.getNombre());
                        nombretinaco.setText(tinaco.getNombre());
                        nombre = tinaco.getNombre();
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
    private void delettinaco() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar este tinaco?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String token = sessionManager.getToken();
                        String authToken = "Bearer " + token;
                        Call<TinacoResponse> call = apiService.deleteTinaco(authToken, tinacoId);
                        call.enqueue(new retrofit2.Callback<TinacoResponse>() {
                            @Override
                            public void onResponse(Call<TinacoResponse> call, retrofit2.Response<TinacoResponse> response) {
                                if (response.isSuccessful()) {
                                    Log.d("DEBUG", "Tinaco eliminado correctamente");
                                    finish(); // Cerrar la actividad
                                } else {
                                    Log.d("DEBUG", "Error en la respuesta de la API: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                                Log.d("DEBUG", "Error al eliminar el tinaco: " + t.getMessage());
                            }
                        });
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}