package com.primerp.integradora.Cosas.Dialog;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.primerp.integradora.R;

public class EditProfileDialogActivity extends AppCompatActivity {

    private EditText editName, editEmail, editPhone;
    private Button saveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_dialog_fragment);

        // Inicializar campos de entrada
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editPhone = findViewById(R.id.edit_phone);

        // Inicializar botones
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Lógica para el botón Guardar
        saveButton.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String email = editEmail.getText().toString();
            String phone = editPhone.getText().toString();

            // Puedes agregar validación aquí
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(EditProfileDialogActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditProfileDialogActivity.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Lógica para el botón Cancelar
        cancelButton.setOnClickListener(v -> finish());
    }
}
