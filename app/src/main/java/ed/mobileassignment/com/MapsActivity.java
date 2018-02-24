package ed.mobileassignment.com;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GlobalVar Var;
    private List lstPOI;
    private Marker lastMarkerClicked = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Var=((GlobalVar)getApplicationContext());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        lstPOI = Var.getListPOI();

        //Inizializzo al di fuori, così da zoomare sull'ultimo marker caricato
        LatLng coord = new LatLng(0,0);
        for (Object obj: lstPOI) {
            POI poi = (POI) obj;
            coord = new LatLng(poi.getLat(), poi.getLng());

            Marker m = mMap.addMarker(new MarkerOptions().position(coord).title(poi.getName()));
            m.setTag(poi);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord,mMap.getMaxZoomLevel()-8));

        /**
         * Possiamo applicare il click principalmente in 2 modi, sul marker o sul titolo. La scelta riguarda prettamente la gestione dell'UX.
         * Cliccando sul marker, non visualizzeremmo il titolo ma andremmo direttamente all'altra pagina. Cliccando sul titolo però, penso che sia poco intuitivo.
         * Per risolvere il problema, memorizzerò in una variabile lastMarkerClicked l'ultimo marker cliccato, in modo da distinguere il click per reperire informazioni
         * (far visualizzare il nome) e il click per entrare nel dettaglio del marker
         *

         mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker arg0) {
          Log.e("arg",arg0.toString());
        }
        });
         */

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (lastMarkerClicked != null && marker.equals(lastMarkerClicked)) {
                    POI poi = (POI) marker.getTag();
                    Log.e("poii",poi.getName());
                }
                lastMarkerClicked = marker;
                return false;
            }
        });


    }

}
