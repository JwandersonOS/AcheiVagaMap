package com.example.wanderson.acheivagamap.Presenter;

import com.example.wanderson.acheivagamap.Model.Estacionamento;
import com.example.wanderson.acheivagamap.Model.Local;
import com.example.wanderson.acheivagamap.Model.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Helio on 08/08/2017.
 */

public class EstacionamentoDao extends GenericDao<Local> {

    private DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference();
    private  DatabaseReference localReference =  databaseReference.child("Estacionamentos");

    @Override
    public boolean salvar(Local local) {
        local.getBairroEstacionamento();
        local.getCidEstacionamento();
        local.getCnpjEstacionamento();
       local.getBairroEstacionamento();
        local.getProprietEstacionamento();
        local.getNomeEstacionamento();
        local.getFoneEstacionamento();
        local.getEmailEstacionamento();
       localReference.push().setValue(local);

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
    public boolean listar(Local local) {
        return false;
    }
}
