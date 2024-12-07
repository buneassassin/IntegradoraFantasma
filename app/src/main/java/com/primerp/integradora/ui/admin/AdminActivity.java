package com.primerp.integradora.ui.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Modelos.User;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.R;
import com.primerp.integradora.ui.notifications.NotificationsFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private ApiService apiService;
    private ImageView profileImage;
    private TextView username, correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ImageView backIcon = findViewById(R.id.iconback);
        Button btnmanageusers = findViewById(R.id.btn_manage_users);
        Button btnviewreports = findViewById(R.id.btn_view_reports);
        Button btnviewrol = findViewById(R.id.btn_rol);
        Button btnviewnotifications=findViewById(R.id.btn_notificacion);

        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.admin_name);
        correo = findViewById(R.id.admin_email);

        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();

        getAdimInfo();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnmanageusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageusers();
            }
        });
        btnviewreports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewreports();
            }
        });
        btnviewrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewrol();
            }
        });
        btnviewnotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewnotification();
            }
        });
    }
    private void getAdimInfo(){
        String token = sessionManager.getToken();
        if (token == null || token.isEmpty()) {
            return;
        }
        String authToken = "Bearer " + token;

        Call<ApiResponse> call = apiService.getAdmin(authToken);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body().getUser();
                    if (user != null) {
                    Toast.makeText(AdminActivity.this, "Bienvenido administrador", Toast.LENGTH_SHORT).show();
                    username.setText(user.getUsuarioNom());
                    correo.setText(user.getEmail());
                    getUserImg();
                }
                } else {
                    Toast.makeText(AdminActivity.this, "Error al obtener información del administrador", Toast.LENGTH_SHORT).show();

            }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("DEBUG", "Error al intentar obtener información del administrador", t);
                Toast.makeText(AdminActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getUserImg() {
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;
        Call<ApiResponse> call = apiService.getimagen(authToken);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body().getUser();
                    if (user != null) {
                        Glide.with(AdminActivity.this)
                                .load(user.getFotoPerfil())
                                .apply(RequestOptions.circleCropTransform())
                                .into(profileImage);
                    }

                } else {
                    Toast.makeText(AdminActivity.this, "Error al obtener la imagen del usuario", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("DEBUG", "Error al intentar obtener la imagen del usuario", t);
                Toast.makeText(AdminActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void manageusers() {
        Intent intent = new Intent(this, AdminManageActivity.class);
        startActivity(intent);
    }
    public void viewreports() {
        Intent intent = new Intent(this, AdminReportsActivity.class);
        startActivity(intent);
    }
    public void viewrol() {
        Intent intent = new Intent(this, AdminRolActivity.class);
        startActivity(intent);
    }
    public void viewnotification() {
        Intent intent = new Intent(this, AdminNotifiacaionesActivity.class);
        startActivity(intent);
    }

}