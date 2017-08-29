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
    TextView viewNome;
    TextView viewLatitude;
    TextView viewLongitude;
    TextView viewQtdVaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhe_marcador);

        viewNome = (TextView) findViewById(R.id.textNomeEstacio);
        viewLatitude = (TextView) findViewById(R.id.textLatitude);
        viewLongitude = (TextView) findViewById(R.id.textLongitude);
        viewQtdVaga = (TextView) findViewById(R.id.textQtdVagas);

        Bundle args = getIntent().getBundleExtra("args_Lista_Marcadores");

        if (args != null) {
            Local local = (Local) args.getSerializable("Local");

            String nomeEstacio = local.getNome();
            double latitude = local.getLatitude();
            double longitude = local.getLongitude();
            int qtdVaga = local.getQtdVagas();


            viewNome.setText(nomeEstacio);
            viewLatitude.setText((int) latitude);
            viewLongitude.setText((int) longitude);
            viewQtdVaga.setText(qtdVaga);
        }
    }


}
