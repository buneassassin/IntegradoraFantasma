package com.primerp.integradora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.ViewModelFactory.RegisterViewModelFactory;
import com.primerp.integradora.Cosas.viewmodel.RegisterViewModel;
import com.primerp.integradora.Login;
import com.primerp.integradora.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText usuario_nomEditText, emailEditText, passwordEditText, nombresEditText, apellidoPaternoEditText, apellidoMaternoEditText, telefonoEditText;
    private Button registerButton;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar vistas
        usuario_nomEditText = findViewById(R.id.usuario_nom);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        nombresEditText = findViewById(R.id.nombres);
        apellidoPaternoEditText = findViewById(R.id.a_p);
        apellidoMaternoEditText = findViewById(R.id.a_m);
        telefonoEditText = findViewById(R.id.telefono);
        registerButton = findViewById(R.id.registerButton);

        // Crear la fábrica de ViewModel
        RegisterViewModelFactory factory = new RegisterViewModelFactory(this);
        viewModel = new ViewModelProvider(this, factory).get(RegisterViewModel.class);

        // Observar resultados del registro
        viewModel.getRegistrationStatus().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Login.class));
                finish();
            } else {
                Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        viewModel.registerUser(
                usuario_nomEditText.getText().toString().trim(),
                emailEditText.getText().toString().trim(),
                passwordEditText.getText().toString().trim(),
                nombresEditText.getText().toString().trim(),
                apellidoPaternoEditText.getText().toString().trim(),
                apellidoMaternoEditText.getText().toString().trim(),
                telefonoEditText.getText().toString().trim()
        );
    }

}
