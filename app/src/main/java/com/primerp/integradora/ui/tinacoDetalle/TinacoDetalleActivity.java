package com.primerp.integradora.ui.tinacoDetalle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.ViewModelFactory.TinacoDetalleViewModelFactory;
import com.primerp.integradora.Cosas.ViewModelFactory.TinacoViewModelFactory;
import com.primerp.integradora.Cosas.viewmodel.TinacoViewModel;
import com.primerp.integradora.R;
import com.primerp.integradora.Cosas.Dialog.EditTinacoDialogActivity;
import com.primerp.integradora.Cosas.viewmodel.TinacoDetalleViewModel;
import com.primerp.integradora.ui.tinacoGrafica.TinacoGraficaActivity;

public class TinacoDetalleActivity extends AppCompatActivity {
    private TinacoDetalleViewModel viewModel;
    private TextView textTitulo, nombretinaco;
    private Button btndelet;
    private int tinacoId;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinaco_detalle);

        // Inicializar UI
        CardView cardEditar = findViewById(R.id.cardEditar);
        ImageView backIcon = findViewById(R.id.iconback);
        ImageView irGarfica = findViewById(R.id.irGarfica);
        textTitulo = findViewById(R.id.textTituloTinaco);
        nombretinaco = findViewById(R.id.nombretinaco);
        btndelet = findViewById(R.id.btn_delet);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this, new TinacoDetalleViewModelFactory(this))
                .get(TinacoDetalleViewModel.class);
        // Obtener ID del intent
        Intent intents = getIntent();
        if (intents != null && intents.hasExtra("TINACO_ID")) {
            tinacoId = intents.getIntExtra("TINACO_ID", -1);
        }

        // Configurar observadores
        observeViewModel();

        // Cargar datos del tinaco
        viewModel.loadTinacoData(tinacoId);
        backIcon.setOnClickListener(v -> finish());

        cardEditar.setOnClickListener(v -> {
            Intent intentedit = new Intent(this, EditTinacoDialogActivity.class);
            intentedit.putExtra("TINACO_ID", tinacoId);
            startActivity(intentedit);
        });
        irGarfica.setOnClickListener(v -> {
            Intent intent = new Intent(this, TinacoGraficaActivity.class);
            intent.putExtra("tinaco_title", nombre);
            startActivity(intent);
        });
        btndelet.setOnClickListener(v -> confirmDeleteTinaco());
    }

    private void observeViewModel() {
        viewModel.getTinacoLiveData().observe(this, tinaco -> {
            if (tinaco != null) {
                textTitulo.setText(tinaco.getNombre());
                nombretinaco.setText(tinaco.getNombre());
                nombre = tinaco.getNombre();
            }
        });

        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Log.d("DEBUG", "Error: " + error);
            }
        });

        viewModel.getDeleteSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Eliminado con éxito", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void confirmDeleteTinaco() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar este tinaco?")
                .setPositiveButton("Sí", (dialog, which) -> viewModel.deleteTinaco(tinacoId))
                .setNegativeButton("No", null)
                .show();
    }

}
