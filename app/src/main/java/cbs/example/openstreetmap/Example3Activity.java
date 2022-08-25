package cbs.example.openstreetmap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions;
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme;

import java.util.ArrayList;
import java.util.List;

import cbs.example.openstreetmap.tool.APIMethod;

public class Example3Activity extends AppCompatActivity {
    private MapView mapView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue("github-glenn1wang-myapp");
        setContentView(R.layout.activity_basic);

        mapView = findViewById(R.id.mapView);
        textView = findViewById(R.id.textView5);

        GeoPoint startPoint = new GeoPoint(25.05397, 121.47309);
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();

        APIMethod apiMethod = new APIMethod(mapView);
        apiMethod.setMapView();
        apiMethod.mapController(startPoint);
        apiMethod.rotationGestureOverlay(getApplicationContext());
        apiMethod.latLonGridlineOverlay2();
        apiMethod.myLocationNewOverlay(getApplicationContext(), textView);
        apiMethod.scaleBarOverlay(displayMetrics);
        apiMethod.minimapOverlay(getApplicationContext(), displayMetrics);

        Marker marker = new Marker(mapView);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setPosition(startPoint);
        marker.setTitle("start point");
        marker.setSubDescription("description");
        marker.setIcon(getResources().getDrawable(R.drawable.ic_launcher_foreground, null));
        marker.setImage(getResources().getDrawable(R.drawable.ic_launcher_background, null));
        //marker.setTextIcon("test");
        mapView.getOverlays().add(marker);

        ArrayList<OverlayItem> items = new ArrayList<>();
        items.add(new OverlayItem("Title" ,"Description", new GeoPoint(25.05300d, 121.47300d)));
        ItemizedOverlayWithFocus<OverlayItem> overlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        Log.i("test", "onItemSingleTapUp");
                        Toast.makeText(getApplicationContext(), "onItemSingleTapUp", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        Log.i("test", "onItemLongPress");
                        Toast.makeText(getApplicationContext(), "onItemLongPress", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }, getApplicationContext());
        overlay.setFocusItemsOnTap(true);
        mapView.getOverlays().add(overlay);

        List<IGeoPoint> points = new ArrayList<>();

        points.add(new LabelledGeoPoint(25.05400d, 121.47400d, "1"));
        points.add(new LabelledGeoPoint(25.05200d, 121.47200d, "2"));

        SimplePointTheme simplePointTheme = new SimplePointTheme(points ,true);

        Paint textStyle = new Paint();
        textStyle.setStyle(Paint.Style.FILL);
        textStyle.setColor(Color.parseColor("#00ff00"));
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(24);

        Paint pointStyle = new Paint();
        pointStyle.setColor(Color.parseColor("#00ff00"));
        //pointStyle.setStrokeWidth(10.0f);

        SimpleFastPointOverlayOptions simpleFastPointOverlayOptions = SimpleFastPointOverlayOptions.getDefaultStyle()
                .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
                .setRadius(15)
                .setIsClickable(true)
                .setCellSize(15)
                .setTextStyle(textStyle)
                .setPointStyle(pointStyle);

        SimpleFastPointOverlay simpleFastPointOverlay = new SimpleFastPointOverlay(simplePointTheme, simpleFastPointOverlayOptions);

        simpleFastPointOverlay.setOnClickListener(new SimpleFastPointOverlay.OnClickListener() {
            @Override
            public void onClick(SimpleFastPointOverlay.PointAdapter points, Integer point) {
                Log.i("test" ,"test");
                Toast.makeText(getApplicationContext()
                        , "You clicked " + ((LabelledGeoPoint) points.get(point)).getLabel()
                        , Toast.LENGTH_SHORT).show();
            }
        });

        mapView.getOverlays().add(simpleFastPointOverlay);

        mapView.addMapListener(mapListener);
    }

    private MapListener mapListener = new MapListener() {
        @Override
        public boolean onScroll(ScrollEvent event) {//移動
            //int x = event.getX();
            //int y = event.getY();
            //Log.i("test", "x:" + x + "y:" + y);
            return false;
        }

        @Override
        public boolean onZoom(ZoomEvent event) {//放大縮小
            double zoomLevel = event.getSource().getZoomLevelDouble();
            //Log.i("test", "zoom level:" + zoomLevel);

            if (zoomLevel == mapView.getMinZoomLevel()){
                Toast.makeText(getApplicationContext(), "世界很大，但地圖很小了", Toast.LENGTH_LONG).show();
            } else if (zoomLevel == mapView.getMaxZoomLevel()){
                Toast.makeText(getApplicationContext(), "已經很大囉，這樣你會知道的太多", Toast.LENGTH_LONG).show();
            }
            return true;
        }
    };

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