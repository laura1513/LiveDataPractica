package com.example.livedatapractica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.livedatapractica.databinding.FragmentTiempoBinding;

public class TiempoFragment extends Fragment {
    private FragmentTiempoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentTiempoBinding.inflate(inflater, container,false)).getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TiempoViewModel tiempoViewModel = new ViewModelProvider(this).get(TiempoViewModel.class);

        tiempoViewModel.obtenerTiempo().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer t) {
                Glide.with(TiempoFragment.this).load(t).into(binding.t);
            }
        });

        tiempoViewModel.obtenerRepeticion().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String repeticion) {
                if (repeticion.equals("CAMBIO")) {
                    binding.cambio.setVisibility(View.VISIBLE);
                } else {
                    binding.cambio.setVisibility(View.GONE);
                }
                binding.repeticion.setText(repeticion);
            }
        });
    }
}