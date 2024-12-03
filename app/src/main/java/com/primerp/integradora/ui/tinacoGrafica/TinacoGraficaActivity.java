package com.primerp.integradora.ui.tinacoGrafica;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.primerp.integradora.R;

import java.util.ArrayList;

public class TinacoGraficaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinaco_grafica);
        String title = getIntent().getStringExtra("tinaco_title");
        TextView titleView = findViewById(R.id.textTitulo);
        titleView.setText(title);

        ImageView backIcon = findViewById(R.id.iconback);
        backIcon.setOnClickListener(v -> finish());

        // Configuración del PieChart
        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40f, "Enero"));
        entries.add(new PieEntry(30f, "Febrero"));
        entries.add(new PieEntry(20f, "Marzo"));
        entries.add(new PieEntry(10f, "Abril"));

        PieDataSet pieDataSet = new PieDataSet(entries, "Distribución mensual");
        pieDataSet.setColors(new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW});
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(14f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDescription(null);
        pieChart.animateY(1000);

        // Configuración del CombinedChart
        CombinedChart combinedChart = findViewById(R.id.combinedChart);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < 12; i++) { // 12 meses
            barEntries.add(new BarEntry(i, (float) (Math.random() * 50 + 10))); // Datos aleatorios
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Consumo por mes");
        barDataSet.setColor(Color.MAGENTA);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(14f);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.3f); // Ajustar el ancho de las barras

        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);

        combinedChart.setData(combinedData);
        combinedChart.setDescription(null);
        combinedChart.animateY(1000);
        combinedChart.setDragEnabled(true);
        combinedChart.setScaleXEnabled(true);
        combinedChart.setScaleYEnabled(false);
        combinedChart.setVisibleXRangeMaximum(6); // Mostrar 6 meses por defecto
        combinedChart.moveViewToX(0);

        // Formatear eje X
        final String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value >= 0 && value < months.length) {
                    return months[(int) value];
                } else {
                    return "";
                }
            }
        });
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(-45);
    }
}
