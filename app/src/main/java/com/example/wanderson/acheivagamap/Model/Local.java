package com.example.wanderson.acheivagamap.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bruno on 9/23/16.
 */

public class Local implements Serializable {

    private String proprietEstacionamento;
    private String nomeEstacionamento;
    private String cnpjEstacionamento;
    private String foneEstacionamento;
    private String endEstacionamento;
    private String bairroEstacionamento;
    private String cidEstacionamento;
    private String EmailEstacionamento;
    private double latitude;
    private double longitude;
    private int qtdVagas;


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nomeEstacionamento", nomeEstacionamento);
        result.put("proprietEstacionamento", proprietEstacionamento);
        result.put("cnpjEstacionamento", cnpjEstacionamento);
        result.put("foneEstacionamento", foneEstacionamento);
        result.put("endEstacionamento", cnpjEstacionamento);
        result.put("bairroEstacionamento", bairroEstacionamento);
        result.put("cidEstacionamento", cidEstacionamento);
        result.put("EmailEstacionamento", EmailEstacionamento);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("qtdVagas", qtdVagas);

        return result;
    }


    public String getProprietEstacionamento() {
        return proprietEstacionamento;
    }

    public void setProprietEstacionamento(String proprietEstacionamento) {
        this.proprietEstacionamento = proprietEstacionamento;
    }

    public String getNomeEstacionamento() {
        return nomeEstacionamento;
    }

    public void setNomeEstacionamento(String nomeEstacionamento) {
        this.nomeEstacionamento = nomeEstacionamento;
    }

    public String getCnpjEstacionamento() {
        return cnpjEstacionamento;
    }

    public void setCnpjEstacionamento(String cnpjEstacionamento) {
        this.cnpjEstacionamento = cnpjEstacionamento;
    }

    public String getFoneEstacionamento() {
        return foneEstacionamento;
    }

    public void setFoneEstacionamento(String foneEstacionamento) {
        this.foneEstacionamento = foneEstacionamento;
    }

    public String getEndEstacionamento() {
        return endEstacionamento;
    }

    public void setEndEstacionamento(String endEstacionamento) {
        this.endEstacionamento = endEstacionamento;
    }

    public String getBairroEstacionamento() {
        return bairroEstacionamento;
    }

    public void setBairroEstacionamento(String bairroEstacionamento) {
        this.bairroEstacionamento = bairroEstacionamento;
    }

    public String getCidEstacionamento() {
        return cidEstacionamento;
    }

    public void setCidEstacionamento(String cidEstacionamento) {
        this.cidEstacionamento = cidEstacionamento;
    }

    public String getEmailEstacionamento() {
        return EmailEstacionamento;
    }

    public void setEmailEstacionamento(String emailEstacionamento) {
        EmailEstacionamento = emailEstacionamento;
    }

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
}
