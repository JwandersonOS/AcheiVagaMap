package com.example.wanderson.acheivagamap.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.wanderson.acheivagamap.Model.Estacionamento;
import com.example.wanderson.acheivagamap.R;

/**
 * Created by Wanderson on 17/06/2017.
 */

public class Activity_Detalhes_Itens extends AppCompatActivity {

    TextView viewProprie;
    TextView viewEstacio;
    TextView viewCnpj;
    TextView viewTelefone;
    TextView viewEndereco;
    TextView viewBairro;
    TextView viewCidade;
    TextView viewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_detalhes_itens);

        viewProprie = (TextView) findViewById(R.id.textProprie);
        viewEstacio = (TextView) findViewById(R.id.textEstacio);
        viewCnpj = (TextView) findViewById(R.id.textCNPJ);
        viewTelefone = (TextView) findViewById(R.id.textTelefone);
        viewEndereco = (TextView) findViewById(R.id.textEndereco);
        viewBairro = (TextView) findViewById(R.id.textBairro);
        viewCidade = (TextView) findViewById(R.id.textCidade);
        viewEmail=(TextView) findViewById(R.id.textEmail);


        Bundle args = getIntent().getBundleExtra("args_Lista_Estacionamento");

        if (args != null) {
            Estacionamento estacionamento = (Estacionamento) args.getSerializable("Estacionamento");

            String nomeProprie = estacionamento.getProprietEstacionamento();
            String nomeEstacio = estacionamento.getNomeEstacionamento();
            String cnpj = estacionamento.getCnpjEstacionamento();
            String telefone = estacionamento.getFoneEstacionamento();
            String endereco = estacionamento.getEndEstacionamento();
            String bairro = estacionamento.getBairroEstacionamento();
            String cidade = estacionamento.getCidEstacionamento();
            String Email= estacionamento.getEmailEstacionamento();

            viewProprie.setText(nomeProprie);
            viewEstacio.setText(nomeEstacio);
            viewCnpj.setText(cnpj);
            viewTelefone.setText(telefone);
            viewEndereco.setText(endereco);
            viewBairro.setText(bairro);
            viewCidade.setText(cidade);
            viewEmail.setText(Email);
        }
    }
}
