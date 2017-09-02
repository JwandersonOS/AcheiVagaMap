package com.example.wanderson.acheivagamap.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanderson.acheivagamap.Detalhes_Marcador;
import com.example.wanderson.acheivagamap.Model.Local;
import com.example.wanderson.acheivagamap.Model.LocalDialog;
import com.example.wanderson.acheivagamap.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Wanderson on 29/07/2017.
 */

public class ActivityMantenedor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener,
        ActivityCompat.OnRequestPermissionsResultCallback, LocalDialog.OnAddMarker, GoogleMap.OnInfoWindowClickListener {
    private Marker marker;
    private LocationManager lm;
    private Location location;
    private Polyline polyline;
    private List<LatLng> list;
    private long distance;

    int currentLeft = 150;

    int currentTop = 0;

    int currentRight = 0;

    int currentBottom = 0;


    private FirebaseDatabase database;

    private static final int REQUEST_PERMISSION = 1;

    private GoogleMap map;

    private static String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenedor);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapMantenedor);
        mapFragment.getMapAsync(this);

        initMaps();
        database = FirebaseDatabase.getInstance();


    }
    public void chamaCadastroUsuario(View v) {
        Intent intent = new Intent(ActivityMantenedor.this, Cadastrar_UsuarioActivity.class);
        startActivity(intent);

    }
    public void chamaCadastroEstacionamento(View v) {
        addPin();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_mantenedor, menu);
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
            Intent intent = new Intent(ActivityMantenedor.this, ActivitySobre.class);
            startActivity(intent);
        }
        if (id == R.id.action_termos) {
            Intent intent = new Intent(ActivityMantenedor.this, Activity_TermosUso.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

               return true;
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

        MapStyleOptions style;
        this.map = map;
        style = new MapStyleOptions("[" +
                "  {" +
                "    \"featureType\":\"poi.business\"," +
                "    \"elementType\":\"all\"," +
                "    \"stylers\":[" +
                "      {" +
                "        \"visibility\":\"off\"" +
                "      }" +
                "    ]" +
                "  }," +
                "  {" +
                "    \"featureType\":\"transit\"," +
                "    \"elementType\":\"all\"," +
                "    \"stylers\":[" +
                "      {" +
                "        \"visibility\":\"off\"" +
                "      }" +
                "    ]" +
                "  }" +
                "]");

        map.setMapStyle(style);
        // map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
        animatePadding(1,80,0,4);

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoContents(Marker marker) {
                TextView tv = new TextView(ActivityMantenedor.this);
                tv.setText(Html.fromHtml("<b><font color=\"#ff0000\">" + marker.getTitle() + ":</font></b> "));


                return tv;
            }

            @Override
            public View getInfoWindow(Marker marker) {
                LinearLayout ll = new LinearLayout(ActivityMantenedor.this);
                ll.setPadding(20, 20, 20, 20);
                ll.setBackgroundColor(Color.GREEN);

                TextView tv = new TextView(ActivityMantenedor.this);
                tv.setText(Html.fromHtml("<b><font color=\"#ffffff\">" + marker.getTitle() + ":</font></b> "));
                ll.addView(tv);

                Button bt = new Button(ActivityMantenedor.this);
                bt.setText("Botão");
                bt.setBackgroundColor(Color.RED);
                bt.setOnClickListener(new Button.OnClickListener() {

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
    public void animatePadding(

            final int toLeft, final int toTop, final int toRight, final int toBottom) {

        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1000;

        final Interpolator interpolator = new OvershootInterpolator();

        final int startLeft = currentLeft;
        final int startTop = currentTop;
        final int startRight = currentRight;
        final int startBottom = currentBottom;

        currentLeft = toLeft;
        currentTop = toTop;
        currentRight = toRight;
        currentBottom = toBottom;

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);

                int left = (int) (startLeft + ((toLeft - startLeft) * t));
                int top = (int) (startTop + ((toTop - startTop) * t));
                int right = (int) (startRight + ((toRight - startRight) * t));
                int bottom = (int) (startBottom + ((toBottom - startBottom) * t));

                map.setPadding(left, top, right, bottom);

                if (elapsed < duration) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent i = new Intent(ActivityMantenedor.this, Detalhes_Marcador.class);
        startActivity(i);
        //Toast.makeText(ActivityPrincipal.this, "Jaqnela de Informação.", Toast.LENGTH_LONG).show();


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
        DatabaseReference locais = database.getReference("estacionamentos");

        locais.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                map.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshots) {
                    Local local = dataSnapshot1.getValue(Local.class);
                    MarkerOptions options = new MarkerOptions();
                    options.position(new LatLng(local.getLatitude(),
                            local.getLongitude())).title(local.getNomeEstacionamento()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_sem_nome));
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
