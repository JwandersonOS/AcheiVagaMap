package com.example.wanderson.acheivagamap.Entidades;

import com.example.wanderson.acheivagamap.DAO.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

    public Estacionamento(){

    }
    public void salvar(){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("estacionamento").child(String.valueOf(getNomeEstacionamento())).setValue(this);

    }
    @Exclude

    public Map<String,Object> toMap(){
        HashMap<String,Object> hashMapEstacionamento = new HashMap<>();

        hashMapEstacionamento.put("proprietario", getProprietEstacionamento());
        hashMapEstacionamento.put("nomeEstacionamento", getNomeEstacionamento());
        hashMapEstacionamento.put("cnpj", getCnpjEstacionamento());
        hashMapEstacionamento.put("telefone", getFoneEstacionamento());
        hashMapEstacionamento.put("endereco", getEndEstacionamento());
        hashMapEstacionamento.put("bairro", getBairroEstacionamento());
        hashMapEstacionamento.put("cidade", getCidEstacionamento());
        hashMapEstacionamento.put("email", getEmailEstacionamento());

        return hashMapEstacionamento;
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

    @Override
    public String toString() {
        return nomeEstacionamento;
    }


}
