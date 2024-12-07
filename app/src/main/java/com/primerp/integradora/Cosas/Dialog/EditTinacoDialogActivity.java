package com.primerp.integradora.Cosas.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Responst.TinacoRequest;
import com.primerp.integradora.Cosas.viewmodel.EditTinacoDialogViewModel;
import com.primerp.integradora.Cosas.ViewModelFactory.EditTinacoDialogViewModelFactory;
import com.primerp.integradora.R;

public class EditTinacoDialogActivity extends AppCompatActivity {
    private EditTinacoDialogViewModel viewModel;
    private EditText editName;
    private Button saveButton;
    private SessionManager sessionManager;
    private int tinacoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tinaco_dialog);

        editName = findViewById(R.id.edit_name);
        saveButton = findViewById(R.id.saveButton);
        sessionManager = new SessionManager(this);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this, new EditTinacoDialogViewModelFactory(this)).get(EditTinacoDialogViewModel.class);

        // Obtener el ID del tinaco
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TINACO_ID")) {
            tinacoId = intent.getIntExtra("TINACO_ID", -1);
        }

        loadTinacoData();
        setupObservers();

        // Botón para guardar cambios
        saveButton.setOnClickListener(v -> updateTinaco());

        ImageView backIcon = findViewById(R.id.iconback);
        backIcon.setOnClickListener(v -> finish());
    }

    private void loadTinacoData() {
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;
        viewModel.fetchTinacoById(authToken, tinacoId);
    }

    private void setupObservers() {
        viewModel.getTinacoLiveData().observe(this, tinaco -> {
            if (tinaco != null) {
                editName.setText(tinaco.getNombre());
            }
        });

        viewModel.getErrorLiveData().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTinaco() {
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;

        String nombre = editName.getText().toString();
        TinacoRequest request = new TinacoRequest(nombre);

        viewModel.updateTinaco(authToken, tinacoId, request);
        Toast.makeText(this, "Tinaco actualizado correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }
}
