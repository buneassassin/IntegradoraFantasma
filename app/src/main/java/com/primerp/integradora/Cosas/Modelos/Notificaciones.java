package com.primerp.integradora.Cosas.Modelos;

import com.google.gson.annotations.SerializedName;

public class Notificaciones {
    private String message;

    @SerializedName("is_read")
    private int isRead; // Cambiar de boolean a int

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

    // Método para obtener isRead como boolean
    public boolean isReadAsBoolean() {
        return isRead == 1; // Considerar 1 como true y 0 como false
    }

}
