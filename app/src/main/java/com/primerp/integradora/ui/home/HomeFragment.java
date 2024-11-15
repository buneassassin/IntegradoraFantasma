package com.primerp.integradora.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.primerp.integradora.Cosas.Adapter.CarruselAdapter;
import com.primerp.integradora.R;
import com.primerp.integradora.databinding.FragmentHomeBinding;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Obtén el ViewModel asociado
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        // Infla el layout del fragmento
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configura el carrusel
        configureCarrusel();

        // Si tienes algún texto en el ViewModel, puedes descomentar y usarlo aquí
        // final TextView textView = binding.textHome;
        // homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    private void configureCarrusel() {
        // Lista de imágenes para el carrusel
        List<Integer> carruselImages = Arrays.asList(
                R.drawable.imagen1,
                R.drawable.imagen2,
                R.drawable.imagen3
        );

        // Configura el adaptador del carrusel
        CarruselAdapter adapter = new CarruselAdapter(requireContext(), carruselImages);
        binding.vpCarrusel.setAdapter(adapter);

        // Configura los indicadores (TabLayout) para el carrusel
        TabLayout tabLayout = binding.tabsCarrusel; // Accediendo directamente a la vista desde el binding
        tabLayout.setupWithViewPager(binding.vpCarrusel, true); // Sincroniza TabLayout con ViewPager
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Desreferencia el binding para evitar fugas de memoria
    }
}
