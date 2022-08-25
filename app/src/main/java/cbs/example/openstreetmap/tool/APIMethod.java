package cbs.example.openstreetmap.tool;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.IOrientationConsumer;
import org.osmdroid.views.overlay.compass.IOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import cbs.example.openstreetmap.Example2Activity;

public class APIMethod extends AppCompatActivity {
    private MapView mapView;
    private MyLocationNewOverlay myLocationNewOverlay;
    public APIMethod(MapView mapView){
        this.mapView = mapView;
    }

    public void setMapView(){
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setVerticalMapRepetitionEnabled(false);
        mapView.setScrollableAreaLimitLatitude(89.9d,-89.9d,0);//45
        mapView.setMultiTouchControls(true);
        mapView.setMaxZoomLevel(22.0d);
        mapView.setMinZoomLevel(3.28d);
    }

    public void mapController(GeoPoint geoPoint){
        IMapController mapController = mapView.getController();
        mapController.setZoom(19.0d);//數字越小地圖越小19
        mapController.setCenter(geoPoint);
    }
    public void maker(GeoPoint geoPoint){
        Marker marker = new Marker(mapView);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setPosition(geoPoint);
        mapView.getOverlays().add(marker);
    }

    public void rotationGestureOverlay(Context context){
        RotationGestureOverlay rotationGestureOverlay = new RotationGestureOverlay(context, mapView);
        rotationGestureOverlay.setEnabled(true);
        mapView.getOverlays().add(rotationGestureOverlay);
    }

    public void latLonGridlineOverlay2(){
        LatLonGridlineOverlay2 latLonGridlineOverlay2 = new LatLonGridlineOverlay2();
        latLonGridlineOverlay2.setFontSizeDp(Short.parseShort("0"));
        mapView.getOverlays().add(latLonGridlineOverlay2);
    }

    public void myLocationNewOverlay(Context context, TextView textView){
        myLocationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context), mapView);
        myLocationNewOverlay.enableMyLocation();
        myLocationNewOverlay.runOnFirstFix(new getCurrentLocation(myLocationNewOverlay, textView));
        mapView.getOverlays().add(myLocationNewOverlay);
    }

    public void scaleBarOverlay(DisplayMetrics displayMetrics){
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(displayMetrics.widthPixels/2, 10);
        mapView.getOverlays().add(scaleBarOverlay);
    }

    public void minimapOverlay(Context context, DisplayMetrics displayMetrics){
        MinimapOverlay minimapOverlay = new MinimapOverlay(context, mapView.getTileRequestCompleteHandler());
        minimapOverlay.setWidth(displayMetrics.widthPixels/5);
        minimapOverlay.setHeight(displayMetrics.heightPixels/5);
        minimapOverlay.setTileSource(TileSourceFactory.WIKIMEDIA);
        mapView.getOverlays().add(minimapOverlay);
    }

    public void compassOverlay(Context context){
        CompassOverlay compassOverlay = new CompassOverlay(context, new IOrientationProvider() {
            @Override
            public boolean startOrientationProvider(IOrientationConsumer orientationConsumer) {
                return false;
            }

            @Override
            public void stopOrientationProvider() {

            }

            @Override
            public float getLastKnownOrientation() {
                return 0;
            }

            @Override
            public void destroy() {

            }
        }, mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);
    }

    class getCurrentLocation implements Runnable{
        private TextView textView;
        private MyLocationNewOverlay myLocationNewOverlay;
        private Handler handler = new Handler();

        public getCurrentLocation(MyLocationNewOverlay myLocationNewOverlay, TextView textView){
            this.myLocationNewOverlay = myLocationNewOverlay;
            this.textView = textView;
        }

        @Override
        public void run() {
            if (myLocationNewOverlay.getMyLocation() == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Searching...");
                    }
                });
            }else {
                Log.d("MyTag", String.format("First location fix: %s", myLocationNewOverlay.getMyLocation().toString()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(String.format("My Location\nLongitude: %s\nLatitude: %s\nAltitude: %s",
                                myLocationNewOverlay.getMyLocation().getLongitude(),
                                myLocationNewOverlay.getMyLocation().getLatitude(),
                                myLocationNewOverlay.getMyLocation().getAltitude()));
                    }
                });
            }
            handler.postDelayed(this, 1000);
        }
    }
}
