package com.primerp.integradora.ui.tinacoGrafica;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Responst.AdminResponse;
import com.primerp.integradora.Cosas.Responst.ReporteResponse;
import com.primerp.integradora.Cosas.Responst.ReporteResponsePorFecha;
import com.primerp.integradora.Cosas.Responst.TinacoRequest;
import com.primerp.integradora.Cosas.Responst.TinacoResponse;
import com.primerp.integradora.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class TinacoGraficaActivity extends AppCompatActivity {
    private int tinacoId;
    private SessionManager sessionManager;
    private  ApiService apiService;
    private TextView Nivelsuciedad, Temperatura, Nivel, EstadodelAgua, Tds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinaco_grafica);
        String title = getIntent().getStringExtra("tinaco_title");
        TextView titleView = findViewById(R.id.textTitulo);
        titleView.setText(title);

        // Inicializar sesión y API
        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();

        Nivelsuciedad = findViewById(R.id.SignificadoNivelsuciedad);
        Temperatura = findViewById(R.id.SignificadoTemperatura);
        Nivel = findViewById(R.id.SignificadoNivel);
        EstadodelAgua = findViewById(R.id.SignificadoEstadodelAgua);
        Tds = findViewById(R.id.SignificadoTds);

        ImageView backIcon = findViewById(R.id.iconback);
        backIcon.setOnClickListener(v -> finish());
        // Obtener el ID del tinaco
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TINACO_ID")) {
            tinacoId = intent.getIntExtra("TINACO_ID", -1);
        }
        String token = sessionManager.getToken();
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token no válido", Toast.LENGTH_SHORT).show();
            return;
        }
        String authToken = "Bearer " + token;
        getph(authToken,tinacoId);
        getturbidez(authToken,tinacoId);
        gettds(authToken,tinacoId);
        getultrasonico(authToken,tinacoId);
        gettemperatura(authToken,tinacoId);
        PieChart(authToken);
        BarChart(authToken);



    }
    private void getph(String authToken, int tinaco_id) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("tinaco_id", tinaco_id);

        Call<TinacoResponse> call = apiService.getph(authToken, requestBody);
        call.enqueue(new retrofit2.Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, retrofit2.Response<TinacoResponse> response) {
                if (response.isSuccessful()) {
                    TinacoResponse tinacoResponse = response.body();
                    if (tinacoResponse != null) {
                        Nivelsuciedad.setText(tinacoResponse.getMensaje());
                    }
                } else {
                    Log.d("API_RESPONSE", "Código de error: " + response.code());
                    Toast.makeText(TinacoGraficaActivity.this, "Error del servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
                Toast.makeText(TinacoGraficaActivity.this, "Error al obtener datos del PH", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getturbidez(String authToken,int tinaco_id){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("tinaco_id", tinaco_id);
        Call<TinacoResponse> call = apiService.getturbidez(authToken, requestBody);
        call.enqueue(new retrofit2.Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, retrofit2.Response<TinacoResponse> response) {
                if (response.isSuccessful()) {
                    TinacoResponse tinacoResponse = response.body();
                    if (tinacoResponse != null) {
                        EstadodelAgua.setText(tinacoResponse.getMensaje());
                    }
                } else {
                    Log.d("API_RESPONSE", "Código de error: " + response.code());
                    Toast.makeText(TinacoGraficaActivity.this, "Error del servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
                Toast.makeText(TinacoGraficaActivity.this, "Error al obtener datos de Turbidez", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void gettemperatura(String authToken,int tinaco_id){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("tinaco_id", tinaco_id);
        Call<TinacoResponse> call = apiService.gettemperatura(authToken, requestBody);
        call.enqueue(new retrofit2.Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, retrofit2.Response<TinacoResponse> response) {
                if (response.isSuccessful()) {
                    TinacoResponse tinacoResponse = response.body();
                    if (tinacoResponse != null) {
                        Temperatura.setText(tinacoResponse.getMensaje());
                    }
                } else {
                    Log.d("API_RESPONSE", "Código de error: " + response.code());
                    Toast.makeText(TinacoGraficaActivity.this, "Error del servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
                Toast.makeText(TinacoGraficaActivity.this, "Error al obtener datos de Temperatura", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getultrasonico(String authToken,int tinaco_id){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("tinaco_id", tinaco_id);
        Call<TinacoResponse> call = apiService.getultrasonico(authToken, requestBody);
        call.enqueue(new retrofit2.Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, retrofit2.Response<TinacoResponse> response) {
                if (response.isSuccessful()) {
                    TinacoResponse tinacoResponse = response.body();
                    if (tinacoResponse != null) {
                        Nivel.setText(tinacoResponse.getMensaje());
                    }
                } else {
                    Log.d("API_RESPONSE", "Código de error: " + response.code());
                    Toast.makeText(TinacoGraficaActivity.this, "Error del servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
                Toast.makeText(TinacoGraficaActivity.this, "Error al obtener datos de Ultrasonido", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void gettds(String authToken,int tinaco_id){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("tinaco_id", tinaco_id);
        Call<TinacoResponse> call = apiService.gettds(authToken, requestBody);
        call.enqueue(new retrofit2.Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, retrofit2.Response<TinacoResponse> response) {
                if (response.isSuccessful()) {
                    TinacoResponse tinacoResponse = response.body();
                    if (tinacoResponse != null) {
                        Tds.setText(tinacoResponse.getMensaje());
                    }
                } else {
                    Log.d("API_RESPONSE", "Código de error: " + response.code());
                    Toast.makeText(TinacoGraficaActivity.this, "Error del servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
                Toast.makeText(TinacoGraficaActivity.this, "Error al obtener datos de TDS", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PieChart1(){
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
    private void PieChart(String authToken) {
        Call<ReporteResponse> call = apiService.obtenerDatos(authToken);

        call.enqueue(new retrofit2.Callback<ReporteResponse>() {
            @Override
            public void onResponse(Call<ReporteResponse> call, retrofit2.Response<ReporteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ReporteResponse.Datos> datos = response.body().getData();

                    if (datos.isEmpty()) {
                        Log.e("PieChart", "No se encontraron datos para graficar.");
                        Toast.makeText(TinacoGraficaActivity.this, "No hay datos disponibles.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Crear las entradas del gráfico
                    ArrayList<PieEntry> entries = new ArrayList<>();
                    for (ReporteResponse.Datos dato : datos) {
                        Log.d("PieChart", "Sensor " + dato.getNombreSensor() + ": " + dato.getCantidadLecturas());
                        entries.add(new PieEntry(dato.getCantidadLecturas(), dato.getNombreSensor())); // Etiqueta correcta
                    }

                    // Crear los colores personalizados
                    List<Integer> colors = new ArrayList<>();
                    colors.add(Color.RED);
                    colors.add(Color.BLUE);
                    colors.add(Color.GREEN);
                    colors.add(Color.YELLOW);
                    colors.add(Color.MAGENTA);
                    colors.add(Color.CYAN);
                    colors.add(Color.LTGRAY);
                    colors.add(Color.DKGRAY);
                    colors.add(Color.WHITE);
                    colors.add(Color.BLACK);

                    // Configurar el gráfico
                    PieDataSet pieDataSet = new PieDataSet(entries, "");
                    pieDataSet.setColors(colors); // Asignar colores personalizados
                    pieDataSet.setValueTextSize(12f); // Tamaño del texto en las entradas

                    PieData pieData = new PieData(pieDataSet);

                    PieChart pieChart = findViewById(R.id.pieChart);
                    pieChart.setData(pieData);
                    pieChart.setHoleRadius(40f); // Tamaño del agujero central
                    pieChart.setCenterText("Sensores"); // Texto central
                    pieChart.setCenterTextSize(16f);
                    pieChart.animateY(1000); // Animación
                    pieChart.invalidate(); // Actualizar gráfico
                } else {
                    Log.e("PieChart", "Respuesta no válida: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ReporteResponse> call, Throwable t) {
                Toast.makeText(TinacoGraficaActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void BarChart(String authToken) {
        Call<ReporteResponsePorFecha> call = apiService.obtenerDatosPorFecha(authToken);

        call.enqueue(new retrofit2.Callback<ReporteResponsePorFecha>() {
            @Override
            public void onResponse(Call<ReporteResponsePorFecha> call, retrofit2.Response<ReporteResponsePorFecha> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ReporteResponsePorFecha.Datos> datos = response.body().getData();

                    if (datos.isEmpty()) {
                        Log.e("BarChart", "No se encontraron datos para graficar.");
                        Toast.makeText(TinacoGraficaActivity.this, "No hay datos disponibles.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ArrayList<BarEntry> entries = new ArrayList<>();
                    ArrayList<String> labels = new ArrayList<>();

                    int index = 0;
                    for (ReporteResponsePorFecha.Datos dato : datos) {
                        Log.d("BarChart", "Fecha " + dato.getFecha() + ": " + dato.getCantidadLecturas());
                        entries.add(new BarEntry(index, dato.getCantidadLecturas()));
                        labels.add(dato.getFecha());
                        index++;
                    }

                    // Crear BarDataSet
                    BarDataSet barDataSet = new BarDataSet(entries, "Cantidad de Lecturas por Fecha");
                    barDataSet.setColor(Color.BLUE); // Color de las barras
                    barDataSet.setValueTextSize(12f);

                    // Crear BarData
                    BarData barData = new BarData(barDataSet);

                    // Crear CombinedData
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(barData); // Agregar BarData al CombinedData

                    // Configurar el CombinedChart
                    CombinedChart combinedChart = findViewById(R.id.combinedChart);
                    combinedChart.setData(combinedData);
                    combinedChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                    combinedChart.getXAxis().setGranularity(1f); // Espaciado entre etiquetas
                    combinedChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                    combinedChart.getAxisLeft().setGranularity(1f);
                    combinedChart.getAxisRight().setEnabled(false); // Deshabilitar eje derecho
                    combinedChart.setDescription(null); // Quitar descripción
                    combinedChart.animateY(1000); // Animación
                    combinedChart.invalidate(); // Actualizar gráfico
                } else {
                    Log.e("BarChart", "Respuesta no válida: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ReporteResponsePorFecha> call, Throwable t) {
                Toast.makeText(TinacoGraficaActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
