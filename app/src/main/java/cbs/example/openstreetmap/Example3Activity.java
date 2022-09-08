package cbs.example.openstreetmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.TilesOverlay;
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
import cbs.example.openstreetmap.tool.CustomIcon;

public class Example3Activity extends AppCompatActivity {
    //Component declaration
    private MapView mapView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue("github-glenn1wang-myapp");
        setContentView(R.layout.activity_basic);

        mapView = findViewById(R.id.mapView);
        textView = findViewById(R.id.textView5);

        //Get the required variables
        GeoPoint startPoint = new GeoPoint(25.05397, 121.47309);
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();

        //Method already used
        APIMethod apiMethod = new APIMethod(mapView);
        apiMethod.setMapView();
        apiMethod.mapController(startPoint);
        apiMethod.maker(new GeoPoint(25.05397, 121.47250), "Compare marker", "Latitude is the same as custom marker");
        apiMethod.rotationGestureOverlay(getApplicationContext());
        apiMethod.latLonGridlineOverlay2();
        apiMethod.myLocationNewOverlay(getApplicationContext(), textView);
        apiMethod.scaleBarOverlay(displayMetrics);
        apiMethod.minimapOverlay(getApplicationContext(), displayMetrics);

        //Custom icon with marker
        CustomIcon customIcon = new CustomIcon(getApplicationContext());
        Marker marker = new Marker(mapView);
        marker.setPosition(startPoint);
        marker.setIcon(new BitmapDrawable(customIcon.compositePicture(R.drawable.f35)));
        marker.setTitle("Custom icon with marker");
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(marker);

        //itemizedOverlayWithFocus method
        ArrayList<OverlayItem> items = new ArrayList<>();
        items.add(new OverlayItem("Title1" ,"Description1", new GeoPoint(25.05300d, 121.47300d)));
        OverlayItem overlayItem = new OverlayItem("Title2" ,"Description2", new GeoPoint(25.05300d, 121.47250d));
        overlayItem.setMarker(new BitmapDrawable(customIcon.compositePicture(R.drawable.f18)));
        items.add(overlayItem);//Custom icon
        ItemizedOverlayWithFocus<OverlayItem> itemizedOverlayWithFocus = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        Log.i(APIMethod.TAG, "onItemSingleTapUp");
                        Toast.makeText(getApplicationContext(), "onItemSingleTapUp", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        Log.i(APIMethod.TAG, "onItemLongPress");
                        Toast.makeText(getApplicationContext(), "onItemLongPress", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }, getApplicationContext());
        itemizedOverlayWithFocus.setFocusItemsOnTap(true);
        mapView.getOverlays().add(itemizedOverlayWithFocus);

        //ItemizedIconOverlay method
        ArrayList<OverlayItem> items2 = new ArrayList<>();
        items2.add(new OverlayItem("Title1" ,"Description1", new GeoPoint(25.05350d, 121.47300d)));
        OverlayItem overlayItem2 = new OverlayItem("Title2" ,"Description2", new GeoPoint(25.05350d, 121.47250d));
        overlayItem2.setMarker(new BitmapDrawable(customIcon.compositePicture(R.drawable.f22)));
        items2.add(overlayItem2);//Custom icon
        ItemizedIconOverlay<OverlayItem> itemItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(items2,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        Log.i(APIMethod.TAG, "onItemSingleTapUp");
                        Toast.makeText(getApplicationContext(), "onItemSingleTapUp", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        Log.i(APIMethod.TAG, "onItemLongPress");
                        Toast.makeText(getApplicationContext(), "onItemLongPress", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }, getApplicationContext());
        mapView.getOverlays().add(itemItemizedIconOverlay);

        //SimpleFastPointOverlay method
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
                Log.i(APIMethod.TAG ,"test");
                Toast.makeText(getApplicationContext()
                        , "You clicked " + ((LabelledGeoPoint) points.get(point)).getLabel()
                        , Toast.LENGTH_SHORT).show();
            }
        });

        mapView.getOverlays().add(simpleFastPointOverlay);
        mapView.addMapListener(mapListener);

        //Failed
        /*MapTileProviderBasic provider = new MapTileProviderBasic(getApplicationContext());
        provider.setTileSource(TileSourceFactory.WIKIMEDIA);
        TilesOverlay tilesOverlay = new TilesOverlay(provider, this.getBaseContext());
        tilesOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);
        mapView.getOverlays().add(tilesOverlay);*/
    }

    private MapListener mapListener = new MapListener() {
        @Override
        public boolean onScroll(ScrollEvent event) {//移動
            int x = event.getX();
            int y = event.getY();
            Log.i(APIMethod.TAG, "x:" + x + "y:" + y);
            return false;
        }

        @Override
        public boolean onZoom(ZoomEvent event) {//放大縮小
            double zoomLevel = event.getSource().getZoomLevelDouble();
            Log.i(APIMethod.TAG, "zoom level:" + zoomLevel);

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