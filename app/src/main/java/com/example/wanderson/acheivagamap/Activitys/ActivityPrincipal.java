package com.example.wanderson.acheivagamap.Activitys;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanderson.acheivagamap.View.ActivityAdmin;
import com.example.wanderson.acheivagamap.View.ActivityCadastrarEstacionamento;
import com.example.wanderson.acheivagamap.View.ActivityFiltro;
import com.example.wanderson.acheivagamap.View.ActivitySobre;
import com.example.wanderson.acheivagamap.View.Activity_Lista_Estacionamento;
import com.example.wanderson.acheivagamap.View.Activity_Recuperar_Senha;
import com.example.wanderson.acheivagamap.View.Activity_TermosUso;
import com.example.wanderson.acheivagamap.View.Detalhe_Marcador;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
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

import java.util.List;

import com.example.wanderson.acheivagamap.Model.Local;
import com.example.wanderson.acheivagamap.Model.LocalDialog;
import com.example.wanderson.acheivagamap.Model.Usuario;
import com.example.wanderson.acheivagamap.R;

public class ActivityPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener,
        ActivityCompat.OnRequestPermissionsResultCallback, LocalDialog.OnAddMarker, GoogleMap.OnInfoWindowClickListener  {

    private EditText edtLogin, edtSenha;
    private Button btnLogin;
    private Usuario usuarios;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private TextView tvCadastro;
    private TextView tvEsqueci;
    private Marker marker;
    private LocationManager lm;
    private Location location;
    private Polyline polyline;
    private List<LatLng> list;
    private long distance;


    private FirebaseDatabase database;

    private static final int REQUEST_PERMISSION = 1;

    private GoogleMap map;

    private static String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nave_esquerda);
        navigationView.setNavigationItemSelectedListener(this);

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

    //CONFIGURAÇÕES DO MAPA//

    private void zoomMapa() {
        LatLng mOrigem;
        mOrigem = new LatLng(-7.204385, -39.318617);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(mOrigem).zoom(16).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

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

        zoomMapa();

        loadMarker();

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){

            @Override
            public View getInfoContents(Marker marker) {
                TextView tv = new TextView(ActivityPrincipal.this);
                tv.setText(Html.fromHtml("<b><font color=\"#ff0000\">"+marker.getTitle()+":</font></b> "));

                return tv;
            }

            @Override
            public View getInfoWindow(Marker marker) {
                LinearLayout ll = new LinearLayout(ActivityPrincipal.this);
                ll.setPadding(20, 20, 20, 20);
                ll.setBackgroundColor(Color.GREEN);

                TextView tv = new TextView(ActivityPrincipal.this);
                tv.setText(Html.fromHtml("<b><font color=\"#ffffff\">"+marker.getTitle()+":</font></b> "));
                ll.addView(tv);

                Button bt = new Button(ActivityPrincipal.this);
                bt.setText("Botão");
                bt.setBackgroundColor(Color.RED);
                bt.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Log.i("Script", "Botão clicado");
                    }

                });

                // ll.addView(bt);

                return ll;
            }

        });

        map.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        TextView viewNome;
        TextView viewLatitude;
        TextView viewLongitude;
        TextView viewQtdVaga;

        viewNome = (TextView) findViewById(R.id.textNomeEstacio);
        viewLatitude = (TextView) findViewById(R.id.textLatitude);
        viewLongitude = (TextView) findViewById(R.id.textLongitude);
        viewQtdVaga = (TextView) findViewById(R.id.textQtdVagas);

        //Intent i = new Intent(ActivityPrincipal.this, Detalhe_Marcador.class);
        //startActivity(i);
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

            Toast.makeText(ActivityPrincipal.this, (CharSequence) viewQtdVaga, Toast.LENGTH_LONG).show();
        }
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
                    MarkerOptions options = new MarkerOptions();
                    options.position(new LatLng(local.getLatitude(),
                            local.getLongitude())).title(local.getNome()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_sem_nome));
                    // map.addMarker(new MarkerOptions().position(new LatLng(local.getLatitude(),
                    //    local.getLongitude())).title(local.getNome()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_sem_nome)));
                    marker = map.addMarker(options);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }


    @Override
    public void onAddMarker() {

    }

}
