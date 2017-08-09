package com.example.wanderson.acheivagamap.Presenter;

import com.example.wanderson.acheivagamap.Model.Estacionamento;
import com.example.wanderson.acheivagamap.Model.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Helio on 08/08/2017.
 */

public class EstacionamentoDao extends GenericDao<Estacionamento> {

    private DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference();
    private  DatabaseReference estacionamentoReference =  databaseReference.child("Estacionamento");

    @Override
    public boolean salvar(Estacionamento estacionamento) {
        estacionamento.getBairroEstacionamento();
        estacionamento.getCidEstacionamento();
        estacionamento.getCnpjEstacionamento();
        estacionamento.getBairroEstacionamento();
        estacionamento.getProprietEstacionamento();
        estacionamento.getNomeEstacionamento();
        estacionamento.getFoneEstacionamento();
        estacionamento.getEmailEstacionamento();
       estacionamentoReference .push().setValue(estacionamento);

        return false;
    }

    @Override
    public void pesquisar(String nome) {

    }

    @Override
    public boolean deletar(int id) {
        return false;
    }

    @Override
    public boolean listar(Estacionamento estacionamento) {
        return false;
    }
}
