package cbs.example.openstreetmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

public class BasicActivity extends AppCompatActivity {
    private MapView mapView;
    private IMapController mapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        mapView = findViewById(R.id.mapView);

        mapView.setTileSource(TileSourceFactory.MAPNIK);

        mapController = mapView.getController();
        mapController.setZoom(5.0d);//數字越小地圖越小19
    }

    public void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }
}