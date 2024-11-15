package com.primerp.integradora.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        configureCarrusel();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void configureCarrusel() {
        List<Integer> carruselImages = Arrays.asList(
                R.drawable.imagen1,
                R.drawable.imagen2,
                R.drawable.imagen3
        );

        // Configura el adaptador
        CarruselAdapter adapter = new CarruselAdapter(requireContext(), carruselImages);
        binding.vpCarrusel.setAdapter(adapter);

        // Configura los indicadores (TabLayout)
        TabLayout tabLayout = binding.getRoot().findViewById(R.id.tabs_carrusel);
        tabLayout.setupWithViewPager(binding.vpCarrusel, true); // Sincroniza
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}