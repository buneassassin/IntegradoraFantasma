package com.primerp.integradora.ui.tinacoDetalle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.Dialog.SensoresActivity;
import com.primerp.integradora.Cosas.ViewModelFactory.TinacoDetalleViewModelFactory;
import com.primerp.integradora.Cosas.ViewModelFactory.TinacoViewModelFactory;
import com.primerp.integradora.Cosas.viewmodel.TinacoViewModel;
import com.primerp.integradora.R;
import com.primerp.integradora.Cosas.Dialog.EditTinacoDialogActivity;
import com.primerp.integradora.Cosas.viewmodel.TinacoDetalleViewModel;
import com.primerp.integradora.ui.tinacoGrafica.TinacoGraficaActivity;

import org.w3c.dom.Text;

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
        CardView irGarfica = findViewById(R.id.cardEstadistica);
        ImageView infoIcon = findViewById(R.id.Info);
        textTitulo = findViewById(R.id.textTituloTinaco);
        nombretinaco = findViewById(R.id.nombretinaco);
        btndelet = findViewById(R.id.btn_delet);

        // Obtener los botones de los sensores
        Button btnPh = findViewById(R.id.btn_Ph);
        Button btnTurbidez = findViewById(R.id.btn_Turbidez);
        Button btnUltrasonico = findViewById(R.id.btn_Ultrasonico);
        Button btnTemperatura = findViewById(R.id.btn_Temperatura);
        Button btnTds = findViewById(R.id.btn_TDS);

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
        infoIcon.setOnClickListener(v -> mostrarCuadroExplicativo());

        cardEditar.setOnClickListener(v -> {
            Intent intentedit = new Intent(this, EditTinacoDialogActivity.class);
            intentedit.putExtra("TINACO_ID", tinacoId);
            startActivity(intentedit);
        });
        irGarfica.setOnClickListener(v -> {
            Intent intent = new Intent(this, TinacoGraficaActivity.class);
            intent.putExtra("TINACO_ID", tinacoId);
            intent.putExtra("tinaco_title", nombre);
            startActivity(intent);
        });
        btndelet.setOnClickListener(v -> confirmDeleteTinaco());

        // Configurar el OnClickListener para cada botón
        btnPh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAActividadConSensor("Ph");
            }
        });

        btnTurbidez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAActividadConSensor("Turbidez");
            }
        });

        btnUltrasonico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAActividadConSensor("Ultrasonico");
            }
        });

        btnTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAActividadConSensor("Temperatura");
            }
        });

        btnTds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAActividadConSensor("TDS");
            }
        });
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
    private void irAActividadConSensor(String nombreSensor) {
        Intent intent = new Intent(TinacoDetalleActivity.this, SensoresActivity.class);  // Cambia 'OtraActividad' por la actividad de destino
        intent.putExtra("nombre_sensor", nombreSensor);  // Enviar el nombre del sensor
        startActivity(intent);
    }

    private void mostrarCuadroExplicativo() {
        // Crea el diálogo
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_explanation);
        dialog.setCancelable(true);

        // Obtén los elementos del diálogo
        TextView tvDialogTitle = dialog.findViewById(R.id.tvDialogTitle);
        TextView tvExplanation = dialog.findViewById(R.id.tvExplanation);
        TextView tvExplanationRed = dialog.findViewById(R.id.tvExplanationRed);
        TextView tvExplanationYellow = dialog.findViewById(R.id.tvExplanationYellow);
        TextView tvExplanationGreen = dialog.findViewById(R.id.tvExplanationGreen);
        Button btnClose = dialog.findViewById(R.id.btnClose);

        // Configura los textos dinámicamente
        tvDialogTitle.setText("Información del Proyecto");
        tvExplanation.setText("Este proyecto monitorea el estado del agua en el tinaco utilizando los siguientes parámetros:\n\n" +
                "1. **Temperatura (°C):**\n" +
                "- Saludables: Entre 20 °C y 35 °C.\n" +
                "- Peligrosos: Menores a 5 °C o mayores a 45 °C.\n\n" +
                "2. **pH:**\n" +
                "- Saludables: Entre 6.5 y 8.5.\n" +
                "- Peligrosos: Fuera de este rango.\n\n" +
                "3. **Turbidez (NTU):**\n" +
                "- Saludables: Menores a 5 NTU.\n" +
                "- Peligrosos: Mayores a 5 NTU.\n\n" +
                "4. **TDS (ppm):**\n" +
                "- Saludables: Menores a 500 ppm.\n" +
                "- Peligrosos: Mayores a 500 ppm.\n\n" +
                "Estos valores ayudan a evaluar la calidad y seguridad del agua almacenada.");
        tvExplanationRed.setText("🔴 Rojo: Indica niveles peligrosos para la salud.");
        tvExplanationYellow.setText("🟡 Amarillo: Muestra condiciones de dudosa calidad.");
        tvExplanationGreen.setText("🟢 Verde: Representa valores seguros y óptimos.");

        // Configura el botón de cierre
        btnClose.setOnClickListener(v -> dialog.dismiss());

        // Muestra el diálogo
        dialog.show();
    }

}
