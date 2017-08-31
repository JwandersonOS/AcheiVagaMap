package com.example.wanderson.acheivagamap.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bruno on 9/23/16.
 */

public class Local implements Serializable {

    private String nomeEstacionamento;
    private String nomeProprietario;
    private double latitude;
    private double longitude;
    private int qtdVagas;

        public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getQtdVagas() {
        return qtdVagas;
    }

    public void setQtdVagas(int qtdVagas) {
        this.qtdVagas = qtdVagas;
    }

    public String getNomeEstacionamento() {
        return nomeEstacionamento;
    }

    public void setNomeEstacionamento(String nomeEstacionamento) {
        this.nomeEstacionamento = nomeEstacionamento;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nomeEstacionamento", nomeEstacionamento);
        result.put("nomeProprietario", nomeProprietario);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("qtdVagas", qtdVagas);

        return result;
    }


}
