package com.primerp.integradora.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Class.User;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Login;
import com.primerp.integradora.R;
import com.primerp.integradora.databinding.FragmentNotificationsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {
    private SessionManager sessionManager;
    private FragmentNotificationsBinding binding;
    private ImageView profileImage;
    private TextView username, nombre, telefono, correo;
    private ApiService apiService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        profileImage = root.findViewById(R.id.profile_image);
        username = root.findViewById(R.id.username);
        nombre = root.findViewById(R.id.Nombre);
        telefono = root.findViewById(R.id.Telefono);
        correo = root.findViewById(R.id.Correo);




        sessionManager = new SessionManager(getContext());
        apiService = RetrofitClient.getInstance(getContext()).getApiService();
        Button logoutButton = binding.btnLogout;

        getUserInfo();
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
        Button editProfile = root.findViewById(R.id.edit_profile);
       /* editProfile.setOnClickListener(view -> {
            // Crear e inicializar el diálogo de edición
            EditProfileDialogFragment dialog = new EditProfileDialogFragment();
            dialog.show(getSupportFragmentManager(), "EditProfileDialog");
        });*/


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void logoutUser() {
        String token = sessionManager.getToken();

        Log.d("DEBUG", "Token recuperado: " + token);

        if (token == null || token.isEmpty()) {
            Toast.makeText(getContext(), "Token no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        String authToken = "Bearer " + token;
        Log.d("DEBUG", "Token con prefijo Bearer: " + authToken);

        Call<ApiResponse> call = apiService.logout(authToken);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("DEBUG", "Respuesta del servidor: " + response.code());
                if (response.isSuccessful()) {
                    sessionManager.clearToken();

                    // Muestra un mensaje al usuario
                    Toast.makeText(getContext(), "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Error al cerrar sesión", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("DEBUG", "Error al intentar cerrar sesión", t);
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserInfo() {
        String token = sessionManager.getToken();

        String authToken = "Bearer " + token;
        Log.d("DEBUG", "Token con prefijo Bearer: " + authToken);
        Call<ApiResponse> call = apiService.getMe(token);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body().getUser();
                    if (user != null) {
                        // Actualizar la interfaz de usuario con la información recibida
                        username.setText(user.getUsuarioNom());
                        nombre.setText(user.getPersona().getNombres() + " " + user.getPersona().getAP() + " " + user.getPersona().getAM());
                        telefono.setText(user.getPersona().getTelefono());
                        correo.setText(user.getEmail());

                        // Cargar imagen de perfil
                        Glide.with(NotificationsFragment.this)
                                .load(user.getFotoPerfil())
                                .apply(RequestOptions.circleCropTransform()) // Aplica la transformación circular
                                .into(profileImage);

                    }
                } else {
                    Toast.makeText(getContext(), "Error al obtener información del usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("DEBUG", "Error al intentar obtener información del usuario", t);
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }

}