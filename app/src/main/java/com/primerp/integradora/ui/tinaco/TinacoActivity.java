package com.primerp.integradora.ui.tinaco;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.ViewModelFactory.TinacoViewModelFactory;
import com.primerp.integradora.Cosas.viewmodel.TinacoViewModel;
import com.primerp.integradora.R;

public class TinacoActivity extends AppCompatActivity {
    private TinacoViewModel tinacoViewModel;
    private EditText nombrEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinaco);

        nombrEditText = findViewById(R.id.nombre);
        Button registerButton = findViewById(R.id.regsirerButton);
        ImageView backIcon = findViewById(R.id.iconback);

        // Configurar ViewModel usando la Factory
        tinacoViewModel = new ViewModelProvider(this, new TinacoViewModelFactory(this))
                .get(TinacoViewModel.class);

        backIcon.setOnClickListener(v -> finish());

        registerButton.setOnClickListener(v -> {
            String nombre = nombrEditText.getText().toString().trim();
            if (!nombre.isEmpty()) {
                tinacoViewModel.addTinaco(nombre).observe(this, response -> {
                    if (response.isSuccess() && response != null ) {
                        Toast.makeText(this, "Tinaco agregado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Error: " + response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
