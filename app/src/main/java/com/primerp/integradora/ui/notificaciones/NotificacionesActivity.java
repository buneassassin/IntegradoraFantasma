package com.primerp.integradora.ui.notificaciones;

import android.app.AlertDialog;
import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.primerp.integradora.Cosas.Adapter.NotificacionAdapter;
import com.primerp.integradora.Cosas.Adapter.TinacoAdapter;
import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Modelos.Notificaciones;
import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.Responst.NotificacionRequest;
import com.primerp.integradora.Cosas.Responst.NotificacionResponse;
import com.primerp.integradora.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class NotificacionesActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private ApiService apiService;
    private TextView messageView;

    private RecyclerView recyclerView;
    private NotificacionAdapter notificacionadapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();
        recyclerView = findViewById(R.id.rv_notificaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageView backIcon = findViewById(R.id.iconback);
        backIcon.setOnClickListener(v -> finish());

        cargarnotificaciones();
    }

    private void cargarnotificaciones() {
        String token = sessionManager.getToken();
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        String authToken = "Bearer " + token;
        notificacionadapter = new NotificacionAdapter(new ArrayList<>(), apiService, authToken);
        recyclerView.setAdapter(notificacionadapter);

        Call<NotificacionResponse> call = apiService.getNotifications(authToken);
        call.enqueue(new retrofit2.Callback<NotificacionResponse>() {
            @Override
            public void onResponse(Call<NotificacionResponse> call, retrofit2.Response<NotificacionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Notificaciones> notificacionesList = response.body().getData();
                    notificacionadapter.updateNotificacionesList(notificacionesList);
                } else {
                    Log.e("API_RESPONSE", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<NotificacionResponse> call, Throwable t) {
                Log.e("API_RESPONSE", "Error: " + t.getMessage());
            }
        });
    }

}