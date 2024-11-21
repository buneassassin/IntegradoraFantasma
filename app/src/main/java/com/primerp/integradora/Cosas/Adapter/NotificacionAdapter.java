package com.primerp.integradora.Cosas.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.primerp.integradora.Cosas.Modelos.Notificaciones;
import com.primerp.integradora.R;

import java.util.List;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.NotificacionViewHolder>{
    private List<Notificaciones> notificacionlist;

    public NotificacionAdapter(List<Notificaciones> tinacoslist) {
        this.notificacionlist = tinacoslist;
    }
    // Método para actualizar la lista de datos
    public void updateNotificacionesList(List<Notificaciones> newNotificacionesList) {
        this.notificacionlist = newNotificacionesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificacionAdapter.NotificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_notificaciones, parent, false);
        return new NotificacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacionAdapter.NotificacionViewHolder holder, int position) {
        Notificaciones notificacion = notificacionlist.get(position);
        Log.d("DEBUG", "Notificación mostrada: " + notificacion.getMessage());
        holder.setData(notificacion);

    }

    @Override
    public int getItemCount() {
        return notificacionlist.size();
    }

    public class NotificacionViewHolder extends RecyclerView.ViewHolder {
        TextView message;


        public NotificacionViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.notification_message);
        }
        public void setData(Notificaciones notificacion) {
            message.setText(notificacion.getMessage());
        }
    }

}
