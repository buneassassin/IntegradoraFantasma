package com.primerp.integradora.Cosas.Responst;

import com.primerp.integradora.Cosas.Class.User;
import com.primerp.integradora.Cosas.Modelos.Tinacos;
import java.util.List;

public class ApiResponse {
    private boolean success;
    private String message;
    private String token;
    private User user; // Agrega esto para manejar la información del usuario

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public User getUser() { // Getter para el objeto User
        return user;
    }


}
