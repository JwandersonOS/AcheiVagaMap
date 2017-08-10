package com.example.wanderson.acheivagamap.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.wanderson.acheivagamap.Model.Estacionamento;
import com.example.wanderson.acheivagamap.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruben on 14/06/2017.
 */

public class Activity_Lista_Estacionamento extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText nomePropri;
    EditText nomeEstacio;
    EditText cnpj;
    EditText telefone;
    EditText endereco;
    EditText bairro;
    EditText cidade;
    EditText Email;

    private DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference();
    private DatabaseReference estacionamentoReference =
            databaseReference.child("Estacionamento");
    private ProgressDialog dialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_estacionamento);

        final ListView listView = (ListView) findViewById(R.id.Lista);

        dialog = ProgressDialog.show(this, "Listando", "Listando os estacionamentos, por favor aguarde...", true, false);

        estacionamentoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dsEstacionamento = dataSnapshot.getChildren();
                List<Estacionamento> estacionamento = new ArrayList<Estacionamento>();
                while (dsEstacionamento.iterator().hasNext()) {
                    DataSnapshot dsObjetoCliente =
                            dsEstacionamento.iterator().next();
                    Estacionamento estacio =
                            dsObjetoCliente.getValue(Estacionamento.class);
                    estacionamento.add(estacio);
                }
                ArrayAdapter<Estacionamento> adapter =
                        new ArrayAdapter<Estacionamento>(Activity_Lista_Estacionamento.this,
                                android.R.layout.simple_list_item_1,
                                estacionamento);
                listView.setAdapter(adapter);

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
            }
        });
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Estacionamento estacionamento = (Estacionamento) parent.getAdapter().getItem(position);

        Bundle args = new Bundle();
        args.putString("nomePropri", String.valueOf(nomePropri));
        args.putString("nomeEstacio", String.valueOf(nomeEstacio));
        args.putString("cnpj", String.valueOf(cnpj));
        args.putString("telefone", String.valueOf(telefone));
        args.putString("endereco", String.valueOf(endereco));
        args.putString("bairro", String.valueOf(bairro));
        args.putString("cidade", String.valueOf(cidade));
        args.putString("Email", String.valueOf(Email));
        args.putSerializable("Estacionamento", estacionamento);

        Intent intent = new Intent(this, Activity_Detalhes_Itens.class);
        intent.putExtra("args_Lista_Estacionamento", args);

        startActivity(intent);
    }
}
