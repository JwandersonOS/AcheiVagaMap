package com.example.wanderson.acheivagamap.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.wanderson.acheivagamap.Model.Estacionamento;
import com.example.wanderson.acheivagamap.Model.Local;
import com.example.wanderson.acheivagamap.R;

/**
 * Created by Wanderson on 23/08/2017.
 */

public class Detalhe_Marcador extends AppCompatActivity {
    TextView viewNomeEstacionamento;
    TextView viewNomeProprietario;
    TextView viewLatitude;
    TextView viewLongitude;
    TextView viewQtdVagas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhe_marcador);

        viewNomeEstacionamento = (TextView) findViewById(R.id.textNomeEstacionamento);
        viewNomeProprietario = (TextView) findViewById(R.id.textNomeProprietario);
        viewLatitude = (TextView) findViewById(R.id.textLatitude);
        viewLongitude = (TextView) findViewById(R.id.textLongitude);
        viewQtdVagas = (TextView) findViewById(R.id.textQtdVagas);

        Bundle args = getIntent().getBundleExtra("args_Lista_Marcadores");

        if (args != null) {
            Local local = (Local) args.getSerializable("Local");

            String nomeEstacionamento = local.getNomeEstacionamento();
            String nomeProprietario = local.getProprietEstacionamento();
            double latitude = local.getLatitude();
            double longitude = local.getLongitude();
            int qtdVagas = local.getQtdVagas();


            viewNomeEstacionamento.setText(nomeEstacionamento);
            viewNomeProprietario.setText(nomeProprietario);
            viewLatitude.setText((int) latitude);
            viewLongitude.setText((int) longitude);
            viewQtdVagas.setText(qtdVagas);
        }
    }


}
