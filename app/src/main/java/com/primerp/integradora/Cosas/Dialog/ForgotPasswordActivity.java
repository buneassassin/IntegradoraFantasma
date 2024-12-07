package com.primerp.integradora.Cosas.Dialog;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.ViewModelFactory.ForgotPasswordViewModelFactory;
import com.primerp.integradora.Cosas.viewmodel.ForgotPasswordViewModel;
import com.primerp.integradora.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ForgotPasswordViewModel viewModel;
    private EditText emailInput;
    private Button sendEmailButton;
    private TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize Views
        emailInput = findViewById(R.id.emailInput);
        sendEmailButton = findViewById(R.id.sendEmailButton);
        backToLogin = findViewById(R.id.backToLogin);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this, new ForgotPasswordViewModelFactory(this))
                .get(ForgotPasswordViewModel.class);

        // Handle Button Clicks
        sendEmailButton.setOnClickListener(v -> forgotPassword());
        backToLogin.setOnClickListener(v -> finish());

        observeViewModel();
    }

    private void observeViewModel() {
        // Observe email validation errors
        viewModel.getEmailError().observe(this, error -> {
            if (error != null) {
                emailInput.setError(error);
            }
        });
    }

    private void forgotPassword() {
        String email = emailInput.getText().toString();
        viewModel.resetPassword(email).observe(this, response -> {
            if (response != null) {
                Toast.makeText(this, "Mensaje de recuperación enviado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
