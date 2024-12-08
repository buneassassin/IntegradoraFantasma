package com.primerp.integradora.Cosas.Responst;

import java.util.List;

public class ReporteResponse {
    private String status;
    private List<Datos> data;

    public static class Datos {
        private int id_sensor;
        private String nombre; // Agregar el nombre del sensor
        private String promedio_valor; // Cambiado a String para coincidir con JSON
        private int cantidad_lecturas;

        public int getIdSensor() {
            return id_sensor;
        }

        public String getNombreSensor() {
            return nombre; // Nuevo getter
        }

        public float getPromedioValor() {
            return Float.parseFloat(promedio_valor); // Convertir a float
        }

        public int getCantidadLecturas() {
            return cantidad_lecturas;
        }
    }

    public String getStatus() {
        return status;
    }

    public List<Datos> getData() {
        return data;
    }
}
