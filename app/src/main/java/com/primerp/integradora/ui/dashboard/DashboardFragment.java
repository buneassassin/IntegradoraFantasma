package com.primerp.integradora.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.primerp.integradora.Cosas.Adapter.TinacoAdapter;
import com.primerp.integradora.Cosas.ViewModelFactory.DashboardViewModelFactory;
import com.primerp.integradora.Cosas.viewmodel.DashboardViewModel;
import com.primerp.integradora.databinding.FragmentDashboardBinding;
import com.primerp.integradora.ui.tinaco.TinacoActivity;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    private TinacoAdapter tinacoAdapter;
    private DashboardViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar RecyclerView
        RecyclerView recyclerView = binding.rvTinacos;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tinacoAdapter = new TinacoAdapter(new ArrayList<>());
        recyclerView.setAdapter(tinacoAdapter);

        // ViewModel
        viewModel = new ViewModelProvider(this, new DashboardViewModelFactory(requireContext()))
                .get(DashboardViewModel.class);

        // Observar datos
        viewModel.getTinacos().observe(getViewLifecycleOwner(), tinacos -> {
            if (tinacos != null && !tinacos.isEmpty()) {
                tinacoAdapter.updateTinacosList(tinacos);
                binding.emptyStateLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                binding.emptyStateLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });

        // Manejo de errores
        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        });

        // Botón para agregar nuevo
        binding.btnAddNew.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TinacoActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
