package com.example.wanderson.acheivagamap.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wanderson.acheivagamap.Entidades.Estacionamento;
import com.example.wanderson.acheivagamap.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityCadastrarEstacionamento extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference estacionamentoReference = databaseReference.child("estacionamento");

    private Button btnSalvar, btnListar;
    private EditText edtPropriet, edtEstac, edtCNPJ,
                     edtTelefone, edtEndereco, edtBairro,
                     edtCidade, edtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_estacionamento_activity);

        edtPropriet = (EditText) findViewById(R.id.edtPropriet);
        edtEstac = (EditText) findViewById(R.id.edtEstac);
        edtCNPJ = (EditText) findViewById(R.id.edtCNPJ);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);
        edtEndereco = (EditText) findViewById(R.id.edtEndereco);
        edtBairro = (EditText) findViewById(R.id.edtBairro);
        edtCidade = (EditText) findViewById(R.id.edtCidade);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Estacionamento estacionamento = new Estacionamento();
                estacionamento.setProprietEstacionamento(edtPropriet.getText().toString());
                estacionamento.setNomeEstacionamento(edtEstac.getText().toString());
                estacionamento.setCnpjEstacionamento(edtCNPJ.getText().toString());
                estacionamento.setFoneEstacionamento(edtTelefone.getText().toString());
                estacionamento.setEndEstacionamento(edtEndereco.getText().toString());
                estacionamento.setBairroEstacionamento(edtBairro.getText().toString());
                estacionamento.setCidEstacionamento(edtCidade.getText().toString());
                estacionamento.setEmailEstacionamento(edtEmail.getText().toString());

                estacionamentoReference.child(edtPropriet.getText().toString()).setValue(estacionamento);
                Toast.makeText(ActivityCadastrarEstacionamento.this, "Estacionamento Salvo." ,
                        Toast.LENGTH_LONG).show();

                edtPropriet.setText("");
                edtEstac.setText("");
                edtCNPJ.setText("");
                edtTelefone.setText("");
                edtEndereco.setText("");
                edtBairro.setText("");
                edtCidade.setText("");
                edtEmail.setText("");

            }
        });
    }
}
