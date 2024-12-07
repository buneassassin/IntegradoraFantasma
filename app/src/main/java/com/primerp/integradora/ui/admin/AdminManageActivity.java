package com.primerp.integradora.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.primerp.integradora.Cosas.Adapter.UserAdapter;
import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Modelos.User;
import com.primerp.integradora.Cosas.ViewModelFactory.AdminManageViewModelFactory;
import com.primerp.integradora.Cosas.viewmodel.AdminManageViewModel;
import com.primerp.integradora.R;

import java.util.ArrayList;
import java.util.List;

public class AdminManageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ProgressBar progressBar;
    private LinearLayout emptyView;
    private AdminManageViewModel viewModel;
    private ApiService apiService;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage);

        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.rv_user);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyView = findViewById(R.id.empty_state_layout);

        ImageView backIcon = findViewById(R.id.iconback);
        backIcon.setOnClickListener(v -> finish());
        String token = sessionManager.getToken();

        userAdapter = new UserAdapter(new ArrayList<>(),apiService,token);
        recyclerView.setAdapter(userAdapter);

        viewModel = new ViewModelProvider(this, new AdminManageViewModelFactory(this)).get(AdminManageViewModel.class);

        observeViewModel();
        fetchUsers();
    }

    private void observeViewModel() {
        viewModel.getUsersLiveData().observe(this, this::updateUI);
        viewModel.getErrorLiveData().observe(this, this::showError);
    }

    private void fetchUsers() {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.fetchUsers();
    }

    private void updateUI(List<User> users) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyView = findViewById(R.id.empty_state_layout);
        String token = sessionManager.getToken();

        progressBar.setVisibility(View.GONE);
        if (users == null || users.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            userAdapter = new UserAdapter(users,apiService,token);
            recyclerView.setAdapter(userAdapter);
        }
    }

    private void showError(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
