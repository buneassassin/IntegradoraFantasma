package com.primerp.integradora.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.primerp.integradora.Cosas.Adapter.TinacoAdapter;
import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.R;
import com.primerp.integradora.databinding.FragmentDashboardBinding;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.ui.tinaco.TinacoActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private SessionManager sessionManager;
    private ApiService apiService;

    private RecyclerView recyclerView;
    private TinacoAdapter tinacoadapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inicializa el ViewModel
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);


        // Infla el layout del fragmento
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sessionManager = new SessionManager(getContext());
        apiService = RetrofitClient.getInstance(getContext()).getApiService();
        // Inicializa el RecyclerView correctamente
        recyclerView = root.findViewById(R.id.rv_tinacos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button AgregarButton = binding.btnAddNew;
        AgregarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TinacoActivity.class);
                startActivity(intent);
            }
        });

        // Configura el RecyclerView
        recyclerView();

        return root;
    }


    public void recyclerView() {
        String token = sessionManager.getToken();

        Log.d("DEBUG", "Token recuperado: " + token);

        if (token == null || token.isEmpty()) {
            Toast.makeText(getContext(), "Token no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Inicializa el adaptador con una lista vacía al inicio
        tinacoadapter = new TinacoAdapter(new ArrayList<>());
        recyclerView.setAdapter(tinacoadapter); // Asigna el adaptador al RecyclerView

        String authToken = "Bearer " + token;
        Log.d("DEBUG", "Token con prefijo Bearer: " + authToken);

        // Llamada a la API para obtener la lista de tinacos
        Call<List<Tinacos>> call = apiService.getTinaco(authToken);
        call.enqueue(new retrofit2.Callback<List<Tinacos>>() {
            @Override
            public void onResponse(Call<List<Tinacos>> call, retrofit2.Response<List<Tinacos>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtener la lista de tinacos directamente
                    List<Tinacos> tinacosList = response.body();

                    Log.d("DEBUG", "Cantidad de tinacos obtenidos: " + tinacosList.size());
                    for (Tinacos tinaco : tinacosList) {
                        Log.d("DEBUG", "Tinaco: " + tinaco.getNombre());
                    }

                    // Actualiza la lista en el adaptador existente
                    tinacoadapter.updateTinacosList(tinacosList);
                } else {
                    Log.e("API_RESPONSE", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Tinacos>> call, Throwable t) {
                Log.d("DEBUG", "Error: " + t.getMessage());
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
