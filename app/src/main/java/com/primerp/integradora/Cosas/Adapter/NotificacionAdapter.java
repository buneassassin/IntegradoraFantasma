package com.primerp.integradora.Cosas.Adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Dialog.NotificationDetailActivity;
import com.primerp.integradora.Cosas.Modelos.Notificaciones;
import com.primerp.integradora.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.NotificacionViewHolder> {
    private List<Notificaciones> notificacionList;
    private ApiService apiService;
    private String authToken;

    public NotificacionAdapter(List<Notificaciones> notificacionList, ApiService apiService, String authToken) {
        this.notificacionList = notificacionList;
        this.apiService = apiService;
        this.authToken = authToken;
    }

    public void updateNotificacionesList(List<Notificaciones> newNotificacionesList) {
        this.notificacionList = newNotificacionesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_notificaciones, parent, false);
        return new NotificacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacionViewHolder holder, int position) {
        Notificaciones notificacion = notificacionList.get(position);
        holder.setData(notificacion);
        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                   .setCancelable(true)
                   .setTitle("Confirmación")
                    .setMessage("¿Estás seguro de que quieres eliminar esta notificación?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        deleteNotification(notificacion.getId(), position);
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();

        });
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), NotificationDetailActivity.class);
            intent.putExtra("notification_title", notificacion.getTitle());
            intent.putExtra("notification_message", notificacion.getMessage());
            intent.putExtra("notification_date", notificacion.getformattedcreatedat());
            intent.putExtra("id", notificacion.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notificacionList.size();
    }

    private void deleteNotification(int notificationId, int position) {
        Call<Void> call = apiService.deleteNotification("Bearer " + authToken, notificationId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    notificacionList.remove(position);
                    notifyItemRemoved(position);
                } else {
                    Log.e("DELETE_API", "Error al eliminar notificación: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("DELETE_API", "Error: " + t.getMessage());
            }
        });
    }

    public static class NotificacionViewHolder extends RecyclerView.ViewHolder {
        TextView message, date, title;
        ImageButton deleteButton;

        public NotificacionViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.notification_message);
            date = itemView.findViewById(R.id.notification_date);
            title = itemView.findViewById(R.id.notification_title);
            deleteButton = itemView.findViewById(R.id.notification_delete_button);
        }

        public void setData(Notificaciones notificacion) {
            message.setText(notificacion.getMessage());
            title.setText(notificacion.getTitle());
            String tiempo = "Hace " + notificacion.getformattedcreatedat();
            date.setText(tiempo);
        }
    }
}
