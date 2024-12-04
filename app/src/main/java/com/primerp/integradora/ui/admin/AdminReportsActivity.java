package com.primerp.integradora.ui.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Modelos.User;
import com.primerp.integradora.Cosas.Responst.AdminResponse;
import com.primerp.integradora.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AdminReportsActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reports);
        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();

        ImageView backIcon = findViewById(R.id.iconBack);
        backIcon.setOnClickListener(v -> finish());


        loadReport();

    }

    private void loadReport() {
        String token = sessionManager.getToken();
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token no válido", Toast.LENGTH_SHORT).show();
            return;
        }
        String authToken = "Bearer " + token;
        Call<AdminResponse> call = apiService.getUserStatistics(authToken);
        call.enqueue(new retrofit2.Callback<AdminResponse>() {
            @Override
            public void onResponse(Call<AdminResponse> call, retrofit2.Response<AdminResponse> response) {
                if (response.isSuccessful()) {
                    AdminResponse adminResponse = response.body();
                    if (adminResponse != null) {
                        populateReports(adminResponse);
                    }
                } else {
                    Toast.makeText(AdminReportsActivity.this, "Error al cargar los reportes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AdminResponse> call, Throwable t) {
                Toast.makeText(AdminReportsActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateReports(AdminResponse adminResponse) {
        LinearLayout reportContainer = findViewById(R.id.reportContainer);

        // Configurar PieChart
        PieChart pieChart = findViewById(R.id.pieChartUsers);
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(adminResponse.getActiveUsers(), "Activos"));
        pieEntries.add(new PieEntry(adminResponse.getInactiveUsers(), "Inactivos"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Usuarios");
        pieDataSet.setColors(new int[]{Color.GREEN, Color.RED});
        pieDataSet.setValueTextSize(14f);
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.invalidate();

        // Configurar BarChart
        BarChart barChart = findViewById(R.id.barChart);
        List<BarEntry> barEntries = new ArrayList<>();
        int index = 0;
        for (AdminResponse.UserByMonth userByMonth : adminResponse.getUsersByMonth()) {
            barEntries.add(new BarEntry(index++, userByMonth.getCount()));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Usuarios por mes");
        barDataSet.setColor(ContextCompat.getColor(this, R.color.purple));
        BarData barData = new BarData(barDataSet);

        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.invalidate();

        // Actualizar texto estático
        TextView totalUsers = findViewById(R.id.totalUsers);
        TextView activeUsers = findViewById(R.id.activeUsers);
        TextView inactiveUsers = findViewById(R.id.inactiveUsers);

        totalUsers.setText(String.valueOf(adminResponse.getTotalUsers()));
        activeUsers.setText(String.valueOf(adminResponse.getActiveUsers()));
        inactiveUsers.setText(String.valueOf(adminResponse.getInactiveUsers()));
    }
}