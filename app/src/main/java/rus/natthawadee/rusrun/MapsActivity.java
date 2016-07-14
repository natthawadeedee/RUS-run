package rus.natthawadee.rusrun;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latRusADouble = 13.859132;
    private double lngARusDouble = 100.481989;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    } //Main Meth



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        //Set Center Map
        LatLng latLng = new LatLng(latRusADouble,lngARusDouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
    } // onmapReady
}//Main class
