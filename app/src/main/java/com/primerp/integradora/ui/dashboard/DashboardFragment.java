package com.primerp.integradora.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Login;
import com.primerp.integradora.databinding.FragmentDashboardBinding;
import com.primerp.integradora.Cosas.Class.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private SessionManager sessionManager;
    private ApiService apiService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inicializa el ViewModel
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        // Inicializa SessionManager
        sessionManager = new SessionManager(getContext());

        // Configura el cliente Retrofit para realizar las peticiones a la API
        apiService = RetrofitClient.getInstance(getContext()).getApiService();

        // Infla el layout del fragmento
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configura el botón de cerrar sesión
        Button logoutButton = binding.logoutButton; // Este es el ID de tu botón de logout

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Realiza la petición de logout
                logoutUser();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Método para realizar la petición de logout
// Método para realizar la petición de logout
    private void logoutUser() {
        // Obtén el token almacenado
        String token = sessionManager.getToken(); // Asegúrate de que sessionManager.getToken() te devuelva el token guardado

        // Depuración: verifica si el token es correcto
        Log.d("DEBUG", "Token recuperado: " + token);

        // Verifica que el token no esté vacío antes de enviar la petición
        if (token == null || token.isEmpty()) {
            Toast.makeText(getContext(), "Token no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Añade el prefijo "Bearer " al token si es necesario
        String authToken = "Bearer " + token;
        Log.d("DEBUG", "Token con prefijo Bearer: " + authToken);

        // Realiza la petición de logout con el token en la cabecera
        Call<ApiResponse> call = apiService.logout(authToken);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("DEBUG", "Respuesta del servidor: " + response.code());
                if (response.isSuccessful()) {
                    // Si la petición es exitosa, elimina el token
                    sessionManager.clearToken();

                    // Muestra un mensaje al usuario
                    Toast.makeText(getContext(), "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();

                    // Redirige al login o MainActivity, según tu lógica
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    getActivity().finish();  // Termina la actividad actual (Dashboard)
                } else {
                    // Si la petición falla, muestra un mensaje de error
                    Toast.makeText(getContext(), "Error al cerrar sesión", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Maneja el error en caso de fallo de la red
                Log.e("DEBUG", "Error al intentar cerrar sesión", t);
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
