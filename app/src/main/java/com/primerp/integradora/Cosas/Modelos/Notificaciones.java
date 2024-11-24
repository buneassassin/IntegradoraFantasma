package com.primerp.integradora.Cosas.Modelos;

import com.google.gson.annotations.SerializedName;

public class Notificaciones {
    private int id;
    private String message;
    private String title;
    @SerializedName("is_read")
    private int isRead;

    @SerializedName("formatted_created_at")
    private int formattedcreatedat;

    public int getformattedcreatedat() {
        return formattedcreatedat;
    }

    public void setformattedcreatedat(int formattedcreatedat) {
        this.formattedcreatedat = formattedcreatedat;
    }

    // Getters y Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public boolean isReadAsBoolean() {
        return isRead == 1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
