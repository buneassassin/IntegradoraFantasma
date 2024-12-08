package com.primerp.integradora.Cosas.Responst;

public class TinacoRequest {
    private String name;
    private int tinaco_id;
    public TinacoRequest(String name) {
        this.name = name;
    }

    public TinacoRequest(int tinaco_id) {
        this.tinaco_id = tinaco_id;
    }

}
