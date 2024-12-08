package com.primerp.integradora.Cosas.Responst;

import java.util.List;

public class ReporteResponsePorFecha {
    private String status;
    private List<Datos> data;

    public String getStatus() {
        return status;
    }

    public List<Datos> getData() {
        return data;
    }

    public static class Datos {
        private String fecha;
        private int cantidad_lecturas;

        public String getFecha() {
            return fecha;
        }

        public int getCantidadLecturas() {
            return cantidad_lecturas;
        }
    }
}
