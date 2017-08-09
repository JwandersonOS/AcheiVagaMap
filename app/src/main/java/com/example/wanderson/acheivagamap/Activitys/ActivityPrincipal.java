package com.example.wanderson.acheivagamap.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wanderson.acheivagamap.Model.Usuario;

import com.example.wanderson.acheivagamap.Fragments.MapsFragment;
import com.example.wanderson.acheivagamap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import static com.example.wanderson.acheivagamap.R.id.edtLogin;

public class ActivityPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   // private EditText edtLogin, edtSenha;
   // private Button btnLogin;
   // private Usuario usuarios;
   // private static final String TAG = "EmailPassword";
   // private FirebaseAuth mAuth;
   // private ProgressDialog dialog;

       private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nave_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.container, new MapsFragment(), "MapsFragment");

        transaction.commitAllowingStateLoss();

      //  edtLogin = (EditText) findViewById(R.id.edtLogin);
        //edtSenha = (EditText) findViewById(R.id.edtSenha);
        //btnLogin = (Button) findViewById(R.id.btnLogin);
       // btnLogin.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //  public void onClick(View v) {
             //   usuarios.setLoginUsuario(edtLogin.getText().toString());
            //    usuarios.setSenhaUsuario(edtSenha.getText().toString());

             //   dialog = ProgressDialog.show(ActivityPrincipal.this, "Autenticando", "Autenticando usuário, por favor aguarde...", true, false);
                //Chama o metodo verificaConexao para checar se o App está conectado a internet
               // if (verificaConexao()) {
                    //Chama o método para autenticar o usuário no banco Firebase
                   // autenticarUsuario(usuarios.getLoginUsuario().toString(),usuarios.getSenhaUsuario().toString());
               // } else {
                 //   Toast.makeText(ActivityPrincipal.this, "Aparentemente você está sem conexão!", Toast.LENGTH_LONG).show();
                 //   dialog.dismiss();
              //  }

              //  edtSenha.setText("");
              //  edtLogin.setText("");

           // }
       // });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_termos) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void chamaLista(View v){
        setContentView(R.layout.lista_estacionamento);
    }
    public void chamaFiltro(View v){
        setContentView(R.layout.activity_filtro);
    }


}
