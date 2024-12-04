package com.primerp.integradora.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.R;
import com.primerp.integradora.Repository.ForgotPasswordRepository;
import com.primerp.integradora.ViewModel.ForgotPasswordViewModel;

public class ForgotPasswordView extends AppCompatActivity {
    private ForgotPasswordViewModel viewModel;
    private EditText emailInput;
    private Button sendEmailButton;
    private TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Inicializar vistas
        emailInput = findViewById(R.id.emailInput);
        sendEmailButton = findViewById(R.id.sendEmailButton);
        backToLogin = findViewById(R.id.backToLogin);

        // Configurar ApiService, Repositorio y ViewModel
        ApiService apiService = RetrofitClient.getInstance(this).getApiService();
        ForgotPasswordRepository repository = new ForgotPasswordRepository(apiService);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
                return (T) new ForgotPasswordViewModel(repository);
            }
        }).get(ForgotPasswordViewModel.class);

        // Configurar eventos del botón
        sendEmailButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            if (email.isEmpty()) {
                emailInput.setError("Por favor, ingrese su correo electrónico");
            } else {
                observeResetPassword(email);
            }
        });

        backToLogin.setOnClickListener(v -> finish());
    }

    private void observeResetPassword(String email) {
        viewModel.resetPassword(email).observe(this, apiResponse -> {
            if (apiResponse != null) {
                Toast.makeText(this, "Mensaje de recuperación enviado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al enviar el mensaje", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
