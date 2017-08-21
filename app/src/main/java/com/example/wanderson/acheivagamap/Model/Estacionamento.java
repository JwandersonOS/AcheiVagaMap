package com.example.wanderson.acheivagamap.Model;

import java.io.Serializable;

/**
 * Created by anton on 11/06/2017.
 */

public class Estacionamento implements Serializable {

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

    @Override
    public String toString() {
        return nomeEstacionamento;
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
}
