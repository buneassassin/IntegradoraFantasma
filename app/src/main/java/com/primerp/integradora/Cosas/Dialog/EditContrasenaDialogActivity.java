package com.primerp.integradora.Cosas.Dialog;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Responst.PassaworRequest;
import com.primerp.integradora.Cosas.ViewModelFactory.EditContrasenaDialogViewModelFactory;
import com.primerp.integradora.Cosas.viewmodel.EditContrasenaDialogViewModel;
import com.primerp.integradora.R;

public class EditContrasenaDialogActivity extends AppCompatActivity {
    private EditContrasenaDialogViewModel viewModel;
    private EditText editcontra, confircontra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contrasena_dialog);

        // Initialize UI components
        editcontra = findViewById(R.id.edit_contra);
        confircontra = findViewById(R.id.confir_contra);
        ImageView backIcon = findViewById(R.id.iconback);

        // Setup ViewModel
        EditContrasenaDialogViewModelFactory factory = new EditContrasenaDialogViewModelFactory(this);
        viewModel = new ViewModelProvider(this, factory).get(EditContrasenaDialogViewModel.class);

        findViewById(R.id.saveButton).setOnClickListener(v -> editcontrasena());
        backIcon.setOnClickListener(v -> finish());
    }

    private void editcontrasena() {
        String password = editcontra.getText().toString();
        String password_confirmation = confircontra.getText().toString();

        if (password.isEmpty() || password_confirmation.isEmpty()) {
            editcontra.setError("Por favor, ingrese una contraseña");
            confircontra.setError("Por favor, confirme la contraseña");
        } else if (!password.equals(password_confirmation)) {
            confircontra.setError("Las contraseñas no coinciden");
        } else {
            String token = "Bearer " + new SessionManager(this).getToken();
            PassaworRequest request = new PassaworRequest(password, password_confirmation);

            // Call ViewModel method and observe result
            viewModel.updatePassword(token, request).observe(this, response -> {
                if (response != null && response.getMessage() != null) {
                    if (response.getMessage().toLowerCase().contains("error")) {
                        Toast.makeText(this, "Error: " + response.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }
    }
}
