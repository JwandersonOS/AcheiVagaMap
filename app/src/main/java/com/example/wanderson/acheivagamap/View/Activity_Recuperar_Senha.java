package com.example.wanderson.acheivagamap.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wanderson.acheivagamap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by Wanderson on 10/08/2017.
 */

public class Activity_Recuperar_Senha extends AppCompatActivity {


    private EditText recemail;
    private Button btnreccusenha;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);
        btnreccusenha = (Button) findViewById(R.id.btnreccusenha);

        firebaseAuth = FirebaseAuth.getInstance();
        btnreccusenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth
                        .sendPasswordResetEmail( recemail.getText().toString() )
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if( task.isSuccessful() ){
                                    recemail.setText("");
                                    Toast.makeText(
                                            Activity_Recuperar_Senha.this,
                                            "RecuperaÃ§Ã£o de acesso iniciada. Email enviado.",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                                else{
                                    Toast.makeText(
                                            Activity_Recuperar_Senha.this,
                                            "Falhou! Tente novamente",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                        });
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init(){
        recemail=(EditText) findViewById(R.id.recemail);
    }


}
