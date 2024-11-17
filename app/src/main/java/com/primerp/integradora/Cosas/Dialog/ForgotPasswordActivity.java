package com.primerp.integradora.Cosas.Dialog;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.R;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ApiService apiService;
    private EditText emailInput;
    private TextView backToLogin;
    private Button sendEmailButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailInput = findViewById(R.id.emailInput);
        sendEmailButton = findViewById(R.id.sendEmailButton);
        backToLogin = findViewById(R.id.backToLogin);

        apiService = RetrofitClient.getInstance(this).getApiService();

        sendEmailButton.setOnClickListener(v -> forgotPassword());
        backToLogin.setOnClickListener(v -> finish());

    }
    private void forgotPassword() {
        String email = emailInput.getText().toString();
        //validate
        if (email.isEmpty()) {
            emailInput.setError("Por favor, ingrese su correo electrónico");
        }else{


            RegisterRequest request  = new RegisterRequest(email);
            Call<ApiResponse> call = apiService.resetPassword(request);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, "Mesaje de recuperacion eviado", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Error al madar mensaje", Toast.LENGTH_SHORT).show();
                        Log.e("API_RESPONSE", "Error: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(ForgotPasswordActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", t.getMessage(), t);
                }
            });


        }

    }
}