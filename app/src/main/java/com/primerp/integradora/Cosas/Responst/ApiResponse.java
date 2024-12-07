package com.primerp.integradora.Cosas.Responst;

import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.Modelos.User;

import java.util.List;

public class ApiResponse {
    private boolean success;
    private String message;
    private String token;
    private User user;

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

    public User getUser() {
        return user;
    }


    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setMessage(String s) {
        this.message = s;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
