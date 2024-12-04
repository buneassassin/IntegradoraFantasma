package com.primerp.integradora.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.primerp.integradora.Cosas.Adapter.UserAdapter;
import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Modelos.User;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AdminManageActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private ApiService apiService;
    private RecyclerView recyclerView;
    private UserAdapter useradapter;
    private ProgressBar progressBar;
    private LinearLayout emptyView; // Agrega esta línea

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();

        recyclerView = findViewById(R.id.rv_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyView = findViewById(R.id.empty_state_layout);

        ImageView backIcon = findViewById(R.id.iconback);
        backIcon.setOnClickListener(v -> finish());

        loaduser();

    }
    private void loaduser() {
        String token = sessionManager.getToken();
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token no válido", Toast.LENGTH_SHORT).show();
            return;
        }
        String authToken = "Bearer " + token;
        Call<List<User>> call = apiService.getusuariosConTinacos(authToken);
        call.enqueue(new retrofit2.Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();

                    if (users == null || users.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        useradapter = new UserAdapter(users,apiService,token);
                        recyclerView.setAdapter(useradapter);
                    }
                } else {
                    Toast.makeText(AdminManageActivity.this, "Error al cargar los usuarios", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(AdminManageActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}