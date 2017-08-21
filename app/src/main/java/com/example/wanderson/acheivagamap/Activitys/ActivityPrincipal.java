package com.example.wanderson.acheivagamap.Activitys;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanderson.acheivagamap.Model.Local;
import com.example.wanderson.acheivagamap.Model.LocalDialog;
import com.example.wanderson.acheivagamap.Model.Usuario;

import com.example.wanderson.acheivagamap.R;
import com.example.wanderson.acheivagamap.View.ActivityAdmin;
import com.example.wanderson.acheivagamap.View.ActivityCadastrarEstacionamento;
import com.example.wanderson.acheivagamap.View.ActivityFiltro;
import com.example.wanderson.acheivagamap.View.ActivitySobre;
import com.example.wanderson.acheivagamap.View.Activity_Lista_Estacionamento;
import com.example.wanderson.acheivagamap.View.Activity_Recuperar_Senha;
import com.example.wanderson.acheivagamap.View.Activity_TermosUso;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener, ActivityCompat.OnRequestPermissionsResultCallback, LocalDialog.OnAddMarker {
    private EditText edtLogin, edtSenha;
    private Button btnLogin;
    private Usuario usuarios;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private TextView tvCadastro;
    private TextView tvEsqueci;

    private LocationManager lm;
    private Location location;
    private double longitude = -7.204385;
    private double latitude = -39.318617;

    private FirebaseDatabase database;

    private static final int REQUEST_PERMISSION = 1;

    private GoogleMap map;


    private static String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawerEsquerda = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggleEsquerda = new ActionBarDrawerToggle(
                this, drawerEsquerda, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerEsquerda.addDrawerListener(toggleEsquerda);
        toggleEsquerda.syncState();

        NavigationView navigationEsquerda = (NavigationView) findViewById(R.id.nave_esquerda);
        navigationEsquerda.setNavigationItemSelectedListener(this);


        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvCadastro = (TextView) findViewById(R.id.tvCadastro);
        tvEsqueci = (TextView) findViewById(R.id.tvEsqueci);

        mAuth = FirebaseAuth.getInstance();
        usuarios = new Usuario();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarios.setLoginUsuario(edtLogin.getText().toString());
                usuarios.setSenhaUsuario(edtSenha.getText().toString());

                dialog = ProgressDialog.show(ActivityPrincipal.this, "Autenticando", "Autenticando usuário, por favor aguarde...", true, false);
                //Chama o metodo verificaConexao para checar se o App está conectado a internet
                if (verificaConexao()) {
                    //Chama o método para autenticar o usuário no banco Firebase
                    autenticarUsuario(usuarios.getLoginUsuario().toString(), usuarios.getSenhaUsuario().toString());
                } else {
                    Toast.makeText(ActivityPrincipal.this, "Aparentemente você está sem conexão!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }

                edtSenha.setText("");
                edtLogin.setText("");

            }
        });
        tvCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPrincipal.this, ActivityCadastrarEstacionamento.class);
                startActivity(intent);
            }
        });

        tvEsqueci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPrincipal.this, Activity_Recuperar_Senha.class);
                startActivity(intent);
            }
        });


        database = FirebaseDatabase.getInstance();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initMaps();

    }

    private void zoomMapa() {
        LatLng mOrigem;
        mOrigem = new LatLng(-7.204385, -39.318617);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(mOrigem).zoom(16).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }


    public boolean verificaConexao() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void autenticarUsuario(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        if ((!email.equals("")) && (!password.equals(""))) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(ActivityPrincipal.this, "Login efetuado com sucesso.",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(ActivityPrincipal.this, ActivityAdmin.class);
                                startActivity(intent);
                            } else {
                                dialog.dismiss();
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(ActivityPrincipal.this, "Login ou senha inválidos.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            dialog.dismiss();
            Toast.makeText(ActivityPrincipal.this, "É obrigatório o preenchimento dos campos E-mail e Senha.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerDireita = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerDireita.isDrawerOpen(GravityCompat.START)) {
            drawerDireita.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
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
        if (id == R.id.action_sobre) {
            Intent intent = new Intent(ActivityPrincipal.this, ActivitySobre.class);
            startActivity(intent);
        }
        if (id == R.id.action_termos) {
            Intent intent = new Intent(ActivityPrincipal.this, Activity_TermosUso.class);
            startActivity(intent);
        }
        if (id == R.id.addMenu) {
            addPin();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        // int id = item.getItemId();

        // if (id == R.id.nav_camera) {

        // Handle the camera action
        // }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void chamaLista(View v) {

        if (verificaConexao()) {
            Intent intent = new Intent(ActivityPrincipal.this, Activity_Lista_Estacionamento.class);
            startActivity(intent);

        } else {
            Toast.makeText(ActivityPrincipal.this, "Aparentemente você está sem conexão!", Toast.LENGTH_LONG).show();

        }
    }

    public void chamaFiltro(View v) {
        Intent intent = new Intent(ActivityPrincipal.this, ActivityFiltro.class);
        startActivity(intent);

    }

   public void initMaps() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions();

        } else {
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 60000, this);
        }

    }


    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        if (lm != null) {
            if (location != null) {
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }


       // map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-7.204385, -39.318617), 15));
        zoomMapa();

        loadMarker();
    }

    public void addPin() {
        LocalDialog localDialog = LocalDialog.getInstance(this);
        localDialog.show(getSupportFragmentManager(), "localDialog");
    }


    @Override
    public void onLocationChanged(Location arg0) {

    }

    @Override
    public void onProviderDisabled(String arg0) {

    }

    @Override
    public void onProviderEnabled(String arg0) {

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Autorizado", Toast.LENGTH_SHORT).show();
                    initMaps();

                } else {
                    Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {


            Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION);
        }
    }

    public void loadMarker() {
        DatabaseReference locais = database.getReference("locais");

        locais.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                map.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshots) {
                    Local local = dataSnapshot1.getValue(Local.class);
                    map.addMarker(new MarkerOptions().position(new LatLng(local.getLatitude(),
                            local.getLongitude())).title(local.getNome())
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_sem_nome)));


                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    @Override
    public void onAddMarker() {
        loadMarker();
    }

}
