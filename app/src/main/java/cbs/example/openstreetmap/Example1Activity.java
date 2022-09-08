package cbs.example.openstreetmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class Example1Activity extends AppCompatActivity {
    //Component declaration
    private MapView mapView;
    //Element declaration
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue("github-glenn1wang-myapp");
        setContentView(R.layout.activity_basic);

        mapView = findViewById(R.id.mapView);

        //MapView Setting
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setVerticalMapRepetitionEnabled(false);
        mapView.setScrollableAreaLimitLatitude(89.9d,-89.9d,0);//45
        mapView.setMultiTouchControls(true);
        mapView.setMaxZoomLevel(22.0d);
        mapView.setMinZoomLevel(3.28d);
        mapView.addMapListener(mapListener);

        //IMapController method
        IMapController mapController = mapView.getController();
        mapController.setZoom(19.0d);//數字越小地圖越小19

        //GeoPoint method
        GeoPoint startPoint = new GeoPoint(25.05397, 121.47309);
        mapController.setCenter(startPoint);

        //Marker method
        marker = new Marker(mapView);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setPosition(startPoint);
        marker.setDraggable(true);
        marker.setOnMarkerDragListener(markerDragListener);
        mapView.getOverlays().add(marker);

        //RotationGestureOverlay method
        RotationGestureOverlay rotationGestureOverlay = new RotationGestureOverlay(getApplicationContext(), mapView);
        rotationGestureOverlay.setEnabled(true);
        mapView.getOverlays().add(rotationGestureOverlay);

        //MapEventsOverlay method
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(mapEventsReceiver);
        mapView.getOverlays().add(mapEventsOverlay);
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

    private MapListener mapListener = new MapListener() {
        @Override
        public boolean onScroll(ScrollEvent event) {//移動
            int x = event.getX();
            int y = event.getY();
            Log.i("OpenStreetMap", "x:" + x + "y:" + y);
            return false;
        }

        @Override
        public boolean onZoom(ZoomEvent event) {//放大縮小
            double zoomLevel = event.getSource().getZoomLevelDouble();
            Log.i("OpenStreetMap", "zoom level:" + zoomLevel);

            if (zoomLevel == mapView.getMinZoomLevel()){
                Toast.makeText(getApplicationContext(), "世界很大，但地圖很小了", Toast.LENGTH_LONG).show();
            } else if (zoomLevel == mapView.getMaxZoomLevel()){
                Toast.makeText(getApplicationContext(), "已經很大囉，這樣你會知道的太多", Toast.LENGTH_LONG).show();
            }
            return true;
        }
    };

    private Marker.OnMarkerDragListener markerDragListener = new Marker.OnMarkerDragListener() {
        @Override
        public void onMarkerDrag(Marker marker) {

        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            Toast.makeText(getApplicationContext(), marker.getPosition().toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMarkerDragStart(Marker marker) {

        }
    };

    private MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
        @Override
        public boolean singleTapConfirmedHelper(GeoPoint p) {
            marker.setPosition(p);
            Toast.makeText(getApplicationContext(), p.toString(), Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public boolean longPressHelper(GeoPoint p) {
            return false;
        }
    };
}

//https://osmdroid.github.io/osmdroid/Markers,-Lines-and-Polygons.html
//http://localhost:63342/pmnabc726c7j819ndjvaa59eh40ubej48ijvz/Open%20Street%20Map/osmdroid-android-6.1.13-javadoc.jar/allclasses.html