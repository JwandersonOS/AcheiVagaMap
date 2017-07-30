package com.example.wanderson.acheivagamap.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wanderson.acheivagamap.Activitys.ActivityAdmin;
import com.example.wanderson.acheivagamap.Entidades.Usuario;
import com.example.wanderson.acheivagamap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends AppCompatActivity {

    private EditText edtLogin, edtSenha;
    private Button btnLogin;
    private Usuario usuarios;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private  ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_activity_principal);

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
        usuarios = new Usuario();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarios.setLoginUsuario(edtLogin.getText().toString());
                usuarios.setSenhaUsuario(edtSenha.getText().toString());

                dialog = ProgressDialog.show(LoginFragment.this, "Autenticando", "Autenticando usuário, por favor aguarde...", true, false);
                //Chama o metodo verificaConexao para checar se o App está conectado a internet
                if (verificaConexao()) {
                    //Chama o método para autenticar o usuário no banco Firebase
                    autenticarUsuario(usuarios.getLoginUsuario().toString(),usuarios.getSenhaUsuario().toString());
                } else {
                    Toast.makeText(LoginFragment.this, "Aparentemente você está sem conexão!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }

                edtSenha.setText("");
                edtLogin.setText("");

            }
        });

    }


    /*
    Método encontrado na internet com o nome isOnline no endereço
    https://pt.stackoverflow.com/questions/29358/testar-conexao-com-a-internet-de-uma-aplica%C3%A7%C3%A3o
    modifiquei para o nome verificaConexao
    */
    public boolean verificaConexao() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }



    /*
    Método encontrado no Git a partir da documentação do Firebase com o nome signIn no endereço
    https://github.com/firebase/quickstart-android/blob/master/auth/app/src/main/java/com/google/firebase/quickstart/auth/EmailPasswordActivity.java
    modifiquei para o nome autenticarUsuario
    */
    private void autenticarUsuario(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        if((!email.equals(""))&&(!password.equals(""))){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginFragment.this, "Login efetuado com sucesso.",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(LoginFragment.this, ActivityAdmin.class);
                                startActivity(intent);
                            } else {
                                dialog.dismiss();
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginFragment.this, "Login ou senha inválidos.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            dialog.dismiss();
            Toast.makeText(LoginFragment.this, "É obrigatório o preenchimento dos campos E-mail e Senha.",
                    Toast.LENGTH_LONG).show();
        }
    }

}
