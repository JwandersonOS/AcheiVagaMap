package com.example.wanderson.acheivagamap.Fragments;

import android.os.Bundle;
import android.view.View;

import com.example.wanderson.acheivagamap.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        LatLng futuroEstac = new LatLng(-7.2030891, -39.3196580);
        mMap.addMarker(new MarkerOptions().position(futuroEstac).title("Futuro Estacionamento").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_maior)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(futuroEstac));

        LatLng estacMotoPark = new LatLng(-7.2029291, -39.3180269);
        mMap.addMarker(new MarkerOptions().position(estacMotoPark).title("Estacionamento Moto Park").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_maior)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(estacMotoPark));

        LatLng estacSaoJose = new LatLng(-7.2040820, -39.3200989);
        mMap.addMarker(new MarkerOptions().position(estacSaoJose).title("Estacionamento São José").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_maior)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(estacSaoJose));

        LatLng estacPlaza = new LatLng(-7.2061060, -39.3190991);
        mMap.addMarker(new MarkerOptions().position(estacPlaza).title("Estacionamento Plaza").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_maior)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(estacPlaza));

        LatLng estacEldorado = new LatLng(-7.2055160, -39.3194038);
        mMap.addMarker(new MarkerOptions().position(estacEldorado).title("Estacionamento Eldorado"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(estacEldorado));
    }

    public void streetView(View v){
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(null , 15);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);

        mMap.moveCamera(location);
        mMap.animateCamera(zoom, 3000, null);
    }
}
