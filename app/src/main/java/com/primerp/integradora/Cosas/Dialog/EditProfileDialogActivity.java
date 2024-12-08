package com.primerp.integradora.Cosas.Dialog;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.ViewModelFactory.EditProfileDialogViewModelFactory;
import com.primerp.integradora.Cosas.viewmodel.EditProfileDialogViewModel;
import com.primerp.integradora.R;

public class EditProfileDialogActivity extends AppCompatActivity {
    private EditProfileDialogViewModel viewModel;
    private EditText editName, editEmail, editPhone, editUser;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_dialog_fragment);

        editUser = findViewById(R.id.edit_user);
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editPhone = findViewById(R.id.edit_phone);
        saveButton = findViewById(R.id.saveButton);

        viewModel = new ViewModelProvider(this, new EditProfileDialogViewModelFactory(this)).get(EditProfileDialogViewModel.class);

        observeUserData();
        saveButton.setOnClickListener(v -> updateUser());
        ImageView backIcon = findViewById(R.id.iconback);
        backIcon.setOnClickListener(v -> finish());
    }

    private void observeUserData() {
        viewModel.getUserData().observe(this, user -> {
            if (user != null) {
                editUser.setText(user.getUsuarioNom());
                editName.setText(user.getPersona().getNombres());
                editEmail.setText(user.getEmail());
                editPhone.setText(user.getPersona().getTelefono());
            } else {
                Toast.makeText(this, "Error al cargar los datos del usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser() {
        String user = editUser.getText().toString();
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String phone = editPhone.getText().toString();

        if (user.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest request = new RegisterRequest(user, name, email, phone);
        viewModel.updateUser(request).observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
