package com.primerp.integradora.Cosas.Responst;

import com.primerp.integradora.Cosas.Modelos.Sensores;
import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.Modelos.User;

import java.util.List;

public class ApiResponse {
    private boolean success;
    private String message;
    private String token;
    private User user;
    private List<Sensores> data;
    private List<User> users;
    private String status;

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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Sensores> getData() {
        return data;
    }

    public void setData(List<Sensores> data) {
        this.data = data;
    }
}
