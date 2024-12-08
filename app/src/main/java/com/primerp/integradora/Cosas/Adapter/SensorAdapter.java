package com.primerp.integradora.Cosas.Adapter;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.primerp.integradora.Cosas.Modelos.Sensores;
import com.primerp.integradora.R;

import java.util.ArrayList;
import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {
    private List<Sensores> sensoresList;

    // Constructor
    public SensorAdapter(List<Sensores> sensoresList) {
        this.sensoresList = sensoresList;
    }
    public void updateData(List<Sensores> newSensoresList) {
        sensoresList.clear();
        sensoresList.addAll(newSensoresList);
        notifyDataSetChanged(); // Notifica al RecyclerView que los datos han cambiado
    }


    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_sensor, parent, false);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, int position) {
        // Obtener el sensor actual
        Sensores sensor = sensoresList.get(position);
        holder.setData(sensor);

    }

    @Override
    public int getItemCount() {
        // Retorna el tamaño de la lista
        return sensoresList != null ? sensoresList.size() : 0;
    }

    // Clase interna ViewHolder
    public class SensorViewHolder extends RecyclerView.ViewHolder {

        // Elementos de la vista
        TextView nombreTextView;
        TextView valorTextView;
        TextView fechaTextView;

        public SensorViewHolder(@NonNull View itemView) {
            super(itemView);

            // Enlazar los elementos del diseño (item_sensor.xml)
            nombreTextView = itemView.findViewById(R.id.sensor_name);
            valorTextView = itemView.findViewById(R.id.sensor_value);
            fechaTextView = itemView.findViewById(R.id.sensor_date);
        }
        public void setData(Sensores sensores) {
            nombreTextView.setText(sensores.getNombre());
            valorTextView.setText(sensores.getValue());
            fechaTextView.setText(sensores.getCreated_at());
        }
    }
}
