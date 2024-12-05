package com.primerp.integradora.Cosas.Adapter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Modelos.User;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userlist;
    private ApiService apiService;
    private String authToken;

/*    public UserAdapter(List<User> userlist) {
        this.userlist = userlist;
    }*/

    public UserAdapter(List<User> userlist, ApiService apiService, String authToken) {
        this.userlist = userlist;
        this.apiService = apiService;
        this.authToken = authToken;
    }

    public List<User> getUserlist() {
        return userlist;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User user = userlist.get(position);
        holder.setData(user);
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    private void blockUser(String userEmail) {
        // Crear un mapa con el correo del usuario
        Map<String, String> emailData = new HashMap<>();
        emailData.put("email", userEmail);

        // Llamar al servicio pasando el token y el mapa con el correo
        Call<ApiResponse> call = apiService.postdesactivarUsuario("Bearer " + authToken, emailData);        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("DEBUG", "Usuario bloqueado con éxito");
                } else {
                    // Aquí mostramos más detalles sobre la respuesta de error
                    Log.d("DEBUG", "Error al bloquear al usuario: " + response.code());
                    Log.d("DEBUG", "Mensaje de error: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.d("DEBUG", "Error detalle: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Error de red o otro problema
                Log.d("DEBUG", "Error de red al bloquear al usuario: " + t.getMessage());
            }
        });
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView userName,userEmail,userRegistrationDate,userTinacosCount,user_role;
        private ImageView userProfilePicture;
        private ImageButton deleteUserButton;
        private View layout;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            // Vincula las vistas
            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_email);
            userRegistrationDate = itemView.findViewById(R.id.user_registration_date);
            userTinacosCount = itemView.findViewById(R.id.user_tinacos_count);
            userProfilePicture = itemView.findViewById(R.id.user_profile_picture);
            deleteUserButton = itemView.findViewById(R.id.ban_user_button);
            user_role = itemView.findViewById(R.id.user_role);
            layout = itemView.findViewById(R.id.layaut);
        }

        public void setData(User user) {
            // Configura los datos del usuario
            userName.setText(user.getUsuarioNom());
            userEmail.setText(user.getEmail());
            userRegistrationDate.setText(user.getTiempoRegistrado()); // Ejemplo, cambiar por cálculo real
            userTinacosCount.setText("Tinacos: " + String.valueOf(user.getNumeroTinacos()));
            user_role.setText("Rol: " + user.getRol());  // Ejemplo, cambiar por cálculo real

            if (user.getFotoPerfil() != null && !user.getFotoPerfil().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(user.getFotoPerfil())
                        .placeholder(R.drawable.ic_user) // Imagen por defecto
                        .apply(RequestOptions.circleCropTransform())
                        .into(userProfilePicture);
            } else {
                userProfilePicture.setImageResource(R.drawable.ic_user); // Imagen por defecto
            }

            if (user.getIsInactive() == 0) {
                deleteUserButton.setVisibility(View.GONE);
                layout.setBackgroundResource(R.drawable.custom_background_gray);
            }

            // Evento para bloquear al usuario con confirmación
            deleteUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Mostrar un diálogo de confirmación antes de bloquear al usuario
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Confirmar Bloqueo")
                            .setMessage("¿Estás seguro de que deseas bloquear a este usuario?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    blockUser(user.getEmail());  // Bloquear al usuario si confirma
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }
    }
}
