package com.primerp.integradora.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.tabs.TabLayout;
import com.primerp.integradora.Cosas.Adapter.CarruselAdapter;

import com.primerp.integradora.Cosas.Dialog.EditProfileDialogActivity;
import com.primerp.integradora.Cosas.ViewModelFactory.HomeViewModelFactory;
import com.primerp.integradora.Cosas.viewmodel.HomeViewModel;
import com.primerp.integradora.R;
import com.primerp.integradora.databinding.FragmentHomeBinding;
import com.primerp.integradora.ui.tinaco.TinacoActivity;


import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModelFactory factory = new HomeViewModelFactory();
        HomeViewModel homeViewModel =
                new ViewModelProvider(this, factory).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel.getCarruselImages().observe(getViewLifecycleOwner(), carruselImages -> {
            configureCarrusel(carruselImages);
        });

        Button AgregarButton = binding.btnAddNew;
        AgregarButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TinacoActivity.class);
            startActivity(intent);
        });

        return root;
    }

    private void configureCarrusel(List<Integer> carruselImages) {
        CarruselAdapter adapter = new CarruselAdapter(requireContext(), carruselImages);
        binding.vpCarrusel.setAdapter(adapter);

        TabLayout tabLayout = binding.tabsCarrusel;
        tabLayout.setupWithViewPager(binding.vpCarrusel, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

