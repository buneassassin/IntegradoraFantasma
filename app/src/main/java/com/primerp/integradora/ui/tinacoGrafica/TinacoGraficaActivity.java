package com.primerp.integradora.ui.tinacoGrafica;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieData;
import com.primerp.integradora.R;

import java.util.ArrayList;

public class TinacoGraficaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinaco_grafica);
        ImageView backIcon = findViewById(R.id.iconback);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Inicializar el gráfico
        PieChart pieChart = findViewById(R.id.pieChart);

        // Crear datos para el gráfico (ejemplo de distribución de porcentaje)
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40f, "Enero"));
        entries.add(new PieEntry(30f, "Febrero"));
        entries.add(new PieEntry(20f, "Marzo"));
        entries.add(new PieEntry(10f, "Abril"));

        // Personalizar el conjunto de datos
        PieDataSet dataSet = new PieDataSet(entries, "Distribución mensual");
        dataSet.setColors(new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW}); // Colores de las secciones
        dataSet.setValueTextColor(Color.BLACK); // Color de texto de los valores
        dataSet.setValueTextSize(14f); // Tamaño del texto de los valores

        // Crear el objeto de datos del gráfico
        PieData pieData = new PieData(dataSet);

        // Asignar datos al gráfico
        pieChart.setData(pieData);
        pieChart.setDescription(null); // Eliminar la descripción por defecto
        pieChart.animateY(1000); // Animación al mostrar el gráfico
    }
}
