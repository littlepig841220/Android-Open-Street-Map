package cbs.example.openstreetmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

public class BasicActivity extends AppCompatActivity {
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        //Component find ID on layout xml.
        mapView = findViewById(R.id.mapView);

        //mapView setting
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        //IMapController setting
        IMapController mapController = mapView.getController();
        mapController.setZoom(5.0d);//數字越小地圖越小19
    }

    /**
     * this will refresh the osmdroid configuration on resuming.
     * if you make changes to the configuration, use
     * SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
     * Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
     */
    public void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    /**
     * this will refresh the osmdroid configuration on resuming.
     * if you make changes to the configuration, use
     * SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
     * Configuration.getInstance().save(this, prefs);
     */
    public void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }
}