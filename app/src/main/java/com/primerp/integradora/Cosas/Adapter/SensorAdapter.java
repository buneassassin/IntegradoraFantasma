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
        TextView color;

        public SensorViewHolder(@NonNull View itemView) {
            super(itemView);

            // Enlazar los elementos del diseño (item_sensor.xml)
            nombreTextView = itemView.findViewById(R.id.sensor_name);
            valorTextView = itemView.findViewById(R.id.sensor_value);
            fechaTextView = itemView.findViewById(R.id.sensor_date);
            color = itemView.findViewById(R.id.sensor_color); // Agrega esto al XML
        }

        public void setData(Sensores sensores) {
            // Obtener el valor del sensor como número (asegúrate de que sea un valor parseable)
            double valor;
            try {
                valor = Double.parseDouble(sensores.getValue());
            } catch (NumberFormatException e) {
                valor = -1; // Valor inválido
            }

            // Determinar el color basado en el tipo de sensor y su valor
            String emoji;

            switch (sensores.getNombre().toLowerCase()) {
                case "ultrasonico":
                    emoji = valor > 10 ? "\uD83D\uDFE2" : "\uD83D\uDD34"; // Verde o Rojo
                    break;

                case "temperatura":
                    if (valor >= 25 && valor <= 40) {
                        emoji = "\uD83D\uDFE2"; // Verde
                    } else if (valor > 24 && valor < 15) {
                        emoji = "\uD83D\uDFE1"; // Amarillo
                    } else {
                        emoji = "\uD83D\uDD34"; // Rojo
                    }
                    break;

                case "ph":
                    if (valor >= 5.5 && valor <= 8.5) {
                        emoji = "\uD83D\uDFE2"; // Verde
                    } else if (valor >= 5.4 && valor < 5 || valor > 8.5 && valor <= 10) {
                        emoji = "\uD83D\uDFE1"; // Amarillo
                    } else {
                        emoji = "\uD83D\uDD34"; // Rojo
                    }
                    break;

                case "turbidez":
                    if (valor >= 0 && valor <= 5) {
                        emoji = "\uD83D\uDFE2"; // Verde
                    } else if (valor > 5 && valor <= 50) {
                        emoji = "\uD83D\uDFE1"; // Amarillo
                    } else {
                        emoji = "\uD83D\uDD34"; // Rojo
                    }
                    break;

                case "tds":
                    if (valor >= 0 && valor <= 50) {
                        emoji = "\uD83D\uDFE2"; // Verde
                    } else if (valor > 50 && valor <= 500) {
                        emoji = "\uD83D\uDFE1"; // Amarillo
                    } else {
                        emoji = "\uD83D\uDD34"; // Rojo
                    }
                    break;

                default:
                    emoji = "\u2753"; // Interrogación
                    break;
            }

            // Asignar los datos a las vistas
            color.setText(emoji);
            nombreTextView.setText(sensores.getNombre());
            valorTextView.setText(sensores.getValue());
            fechaTextView.setText(sensores.getCreated_at());
        }
    }
}
