package com.primerp.integradora.ui.notifications;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.primerp.integradora.Cosas.Modelos.User;
import com.primerp.integradora.Cosas.Dialog.EditContrasenaDialogActivity;
import com.primerp.integradora.Cosas.Dialog.EditProfileDialogActivity;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Login;
import com.primerp.integradora.R;
import com.primerp.integradora.databinding.FragmentNotificationsBinding;
import com.primerp.integradora.ui.admin.AdminActivity;
import com.primerp.integradora.ui.notificaciones.NotificacionesActivity;
import com.primerp.integradora.ui.tinaco.TinacoActivity;

import org.jetbrains.annotations.Nullable;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {
    private SessionManager sessionManager;
    private FragmentNotificationsBinding binding;
    private ImageView profileImage;
    private TextView username, nombre, telefono, correo;
    private ApiService apiService;
    private static final int PICK_IMAGE = 1;
    private static final int TAKE_PHOTO = 2;
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


        Button admin = binding.buttonAdmin;
        Button AgregarButton = binding.btnAddNew;
        Button logoutButton = binding.btnLogout;
        Button editperfil = binding.editProfile;
        Button notificacionesButton = binding.btnNotificacion;
        ImageButton editarContrasena = binding.editContrasena;
        ImageView Ver= binding.profileImage;

        getUserImg();
        getUserInfo();
        getAdimInfo();

        editperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileDialogActivity.class);
                startActivity(intent);

            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
        editarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarContrasena();
            }
        });
        Ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog();
            }
        });
        AgregarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TinacoActivity.class);
                startActivity(intent);
            }
        });
        notificacionesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NotificacionesActivity.class);
                startActivity(intent);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Admin();
            }
        });
        return root;
    }

    private void getAdimInfo(){
        Button admin = binding.buttonAdmin;
        String token = sessionManager.getToken();

        if (token == null || token.isEmpty()) {
            return;
        }

        String authToken = "Bearer " + token;
        Call<ApiResponse> call = apiService.getAdmin(authToken);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("DEBUG", "Respuesta del servidor: " + response.code());
                if (response.isSuccessful()) {
                    admin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("DEBUG", "Error al intentar obtener información del administrador", t);
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

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
                        username.setText(user.getUsuarioNom());
                        nombre.setText(user.getPersona().getNombres() + " " + user.getPersona().getAP() + " " + user.getPersona().getAM());
                        telefono.setText(user.getPersona().getTelefono());
                        correo.setText(user.getEmail());
                        getUserImg();

                        /*Glide.with(NotificationsFragment.this)
                                .load(user.getFotoPerfil())
                                .apply(RequestOptions.circleCropTransform())
                                .into(profileImage);*/
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
    private void getUserImg() {
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;

        Log.d("DEBUG", "Token con prefijo Bearer: " + authToken);
        Call<ApiResponse> call = apiService.getimagen(authToken);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body().getUser();
                    if (user != null) {
                        Glide.with(NotificationsFragment.this)
                                .load(user.getFotoPerfil())
                                .apply(RequestOptions.circleCropTransform())
                                .into(profileImage);
                    }
                } else {
                    Toast.makeText(getContext(), "Error al obtener la imagen del usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("DEBUG", "Error al intentar obtener la imagen del usuario", t);
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void editarContrasena() {
        Intent intent = new Intent(getActivity(), EditContrasenaDialogActivity.class);
        startActivity(intent);
    }
    public void Admin() {
        Intent intent = new Intent(getActivity(), AdminActivity.class);
        startActivity(intent);
    }
    private void showImageDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_image_preview);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView imagePreview = dialog.findViewById(R.id.image_preview);
        Button buttonEdit = dialog.findViewById(R.id.button_edit);

        imagePreview.setImageDrawable(profileImage.getDrawable());

        buttonEdit.setOnClickListener(v -> {
            dialog.dismiss();
            openImagePicker();
        });

        dialog.show();
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE && data != null) {
                Uri selectedImageUri = data.getData();
                profileImage.setImageURI(selectedImageUri);
                uploadImageToServer(selectedImageUri);
            }
        }
    }
    private void uploadImageToServer(Uri imageUri) {
        File file = new File(getRealPathFromURI(imageUri));
        String token = sessionManager.getToken();

        String authToken = "Bearer " + token;
        Log.d("DEBUG", "Token con prefijo Bearer: " + authToken);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<ApiResponse> call = apiService.uploadImage(authToken, body);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Imagen subida correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al subir la imagen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}