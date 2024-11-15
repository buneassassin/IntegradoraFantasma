package com.primerp.integradora.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Login;
import com.primerp.integradora.MainActivity;
import com.primerp.integradora.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private SessionManager sessionManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inicializa el ViewModel
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        // Inicializa SessionManager
        sessionManager = new SessionManager(getContext());

        // Infla el layout del fragmento
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configura el botón de cerrar sesión
        Button logoutButton = binding.logoutButton; // Este es el ID de tu botón de logout

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cierra sesión y elimina el token
                sessionManager.logout();

                // Redirige al login o MainActivity, según tu lógica
                Intent intent = new Intent(getActivity(), Login.class);  // Cambia esta línea si necesitas redirigir al Login
                startActivity(intent);
                getActivity().finish();  // Termina la actividad actual (Dashboard)
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
