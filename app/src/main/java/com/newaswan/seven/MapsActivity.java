package com.newaswan.seven;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, LocationListener,
        ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener ,  View.OnClickListener {
    LocationManager locationManager;
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private double longitude;
    private double latitude;
    private GoogleApiClient googleApiClient;
    private FloatingActionMenu fam;
    private com.github.clans.fab.FloatingActionButton fabEdit, fabDelete, fabAdd ,fabgas ,fabuniteheeling;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Initializing googleApiClient
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        fabEdit = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab1);
        fabEdit.setOnClickListener(this);
        fabAdd = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab2);
        fabAdd.setOnClickListener(this);
        fabDelete = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab3);
        fabDelete.setOnClickListener(this);
        fabgas = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab4);
        fabgas.setOnClickListener(this);
        fabuniteheeling = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.fabuniteheeling);
        fabuniteheeling.setOnClickListener(this);

        fam = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fam.setMenuButtonColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_dark));
        fam.setMenuButtonColorPressed(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_dark));
        fam.setMenuButtonColorRipple(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_dark));
        fabAdd.setColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.app_primary));
        fabAdd.setColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.app_primary));
        fabAdd.setColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.app_primary));
        fabDelete.setColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.colorPrs));
        fabDelete.setColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.colorPrs));
        fabDelete.setColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.colorPrs));
        fabEdit.setColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.com_facebook_blue));
        fabEdit.setColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.com_facebook_blue));
        fabEdit.setColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.com_facebook_blue));
        //handling menu status (open or close)
        fam.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    // showToast("");
                    //   fam.setMenuButtonColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_dark));
                } else {
                    //  showToast("Menu is closed");
                    //  fam.setMenuButtonColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_dark));
                }
            }
        });
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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnInfoWindowClickListener(this);
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_json));
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json);
        mMap.setMapStyle(style);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                    mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
            }
        });
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(24.1896670, 32.8437700); // مبني جهاز المدينة
        LatLng ho = new LatLng(24.194056, 32.866333);  // الكوبري المعلق
        LatLng cornash = new LatLng(24.190324, 32.862872); // الشريط النهري
        LatLng elk = new LatLng(24.180505, 32.839153); // موقع محطة المحولات
        LatLng watr = new LatLng(24.189037, 32.839164); // موقع محطة مياه الشرب
        LatLng univ = new LatLng(24.168175, 32.844843); // جامعة اسوان
        LatLng gas = new LatLng(24.180540, 32.850590); // محطة البنزين
        LatLng langschool = new LatLng(24.193565, 32.845299); // مدرسة لغات
        LatLng uniteheeling = new LatLng(24.189810, 32.850256); // الوحدة العلاجية
        LatLng masjed = new LatLng(24.186665, 32.851144); // المسجد الجامع
        LatLng school1 = new LatLng(24.189061, 32.844717); // مدرسة اعدادي 7195 م
        LatLng school2 = new LatLng(24.188476, 32.845578); // مدرسة ابتدائي 5593 م
        LatLng masjed2 = new LatLng(24.189205, 32.846659); //  المسجد الجامع 8100 م2
        LatLng markz = new LatLng(24.200030, 32.850142); // مركز التنمية الرياضي
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsidepink_73053)).position(markz).title(" مركز التنمية الرياضي  ").snippet("5593 م2").draggable(true));
        LatLng kidgarden = new LatLng(24.204572, 32.852941); // حضانة الهلال الاحمر
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsidepink_73053)).position(kidgarden).title(" حضانة الهلال الاحمر  ").snippet("5593 م2").draggable(true));
        LatLng caps = new LatLng(24.206751, 32.850090); // قسم شرطة أسوان الجديدة
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsidepink_73053)).position(caps).title(" قسم شرطة أسوان الجديدة ").snippet("5593 م2").draggable(true));
        LatLng bulding = new LatLng(24.193478, 32.846364); // عمارات الاسكان الاقتصادي
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsidepink_73053)).position(bulding).title(" عمارات الاسكان الاقتصادي ").snippet("5593 م2").draggable(true));
        LatLng bulding1 = new LatLng(24.192391, 32.845842); // عمارات الاسكان الاقتصادي
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsidepink_73053)).position(bulding1).title(" عمارات الاسكان الاقتصادي ").snippet("5593 م2").draggable(true));
        LatLng bulding2 = new LatLng(24.192421, 32.846540); // عمارات الاسكان الاجتماعي شركة المدائن عقد 6
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsidepink_73053)).position(bulding2).title("  عمارات الاسكان الاجتماعي شركة المدائن عقد 6").snippet("5593 م2").draggable(true));
        LatLng bulding3 = new LatLng(24.190336, 32.844628); // عمارات الاسكان الاجتماعي شركة المحمدية 22 عمارة
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsidepink_73053)).position(bulding3).title(" عمارات الاسكان الاجتماعي شركة المحمدية 22 عمارة ").snippet("5593 م2").draggable(true));
        LatLng land = new LatLng(24.192031, 32.841043); // قطع اراضي اسكان اجتماعي 276 اهالي
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsidepink_73053)).position(land).title(" قطع اراضي اسكان اجتماعي 276 اهالي ").snippet("5593 م2").draggable(true));
        LatLng kidgarden2 = new LatLng(24.193613, 32.845297); // حضانة بالحي الثاني
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsidepink_73053)).position(kidgarden2).title(" حضانة بالحي الثاني").snippet("5593 م2").draggable(true));
        LatLng footfive = new LatLng(24.195182, 32.846316); // ملعب خماسي بالحي الثاني
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsidepink_73053)).position(footfive).title(" ملعب خماسي بالحي الثاني").snippet("5593 م2").draggable(true));
        LatLng school3 = new LatLng(24.196402, 32.846392); // مدرسة تعليم اساسي 42 فصل بالحي الثاني
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubbleazure_73024)).position(school3).title(" مدرسة تعليم اساسي 42 فصل بالحي الثاني ").snippet("5593 م2").draggable(true));
        LatLng land1 = new LatLng(24.196527, 32.844336); // قطع اراضي اسكان اقتصادي خدمي وحرفي
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubbleazure_73024)).position(land1).title(" قطع اراضي اسكان اقتصادي خدمي وحرفي ").snippet("5593 م2").draggable(true));
        LatLng mole1 = new LatLng(24.195165, 32.842447); // مول تجاري بالحي الثاني
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubbleazure_73024)).position(mole1).title("مول تجاري بالحي الثاني ").snippet("5593 م2").draggable(true));
        LatLng footfive1 = new LatLng(24.195065, 32.841618); // ملعب خماسي بالحي الثاني بعمارات الاسكان الاجتماعي
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubbleazure_73024)).position(footfive1).title(" ملعب خماسي بالحي الثاني بعمارات الاسكان الاجتماعي ").snippet("5593 م2").draggable(true));
        LatLng bulding4 = new LatLng(24.199452, 32.842084); // عمارات الاسكان الاجتماعي عقد 1 المكتب العربي
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerflagpink_73044)).position(bulding4).title(" عمارات الاسكان الاجتماعي عقد 1 المكتب العربي ").snippet("5593 م2").draggable(true));
        LatLng bulding5 = new LatLng(24.198018, 32.842287); // عمارات الاسكان الاجتماعي عقد 3 المكتب العربي
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerflagpink_73044)).position(bulding5).title(" عمارات الاسكان الاجتماعي عقد 3 المكتب العربي ").snippet("5593 م2").draggable(true));
        LatLng bulding6 = new LatLng(24.197897, 32.841168); // عمارات الاسكان الاجتماعي عقد 2 المدائن
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerflagpink_73044)).position(bulding6).title("  عمارات الاسكان الاجتماعي عقد 2 المدائن ").snippet("5593 م2").draggable(true));
        LatLng bulding7 = new LatLng(24.194655, 32.841174); // عمارات الاسكان الاجتماعي عقد 4 المدائن
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerflagpink_73044)).position(bulding7).title(" عمارات الاسكان الاجتماعي عقد 4 المدائن ").snippet("5593 م2").draggable(true));
        LatLng bulding8 = new LatLng(24.196355, 32.842277); // حضانة بالحي الثاني عمارات الاسكان الاجتماعي
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubblechartreuse_73025)).position(bulding8).title("حضانة بالحي الثاني عمارات الاسكان الاجتماعي ").snippet("5593 م2").draggable(true));
        LatLng hospital1 = new LatLng(24.200900, 32.842450); // وحدة صحية بالحي الثاني منطقة ابني بيتك 1
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubblechartreuse_73025)).position(hospital1).title("  وحدة صحية بالحي الثاني منطقة ابني بيتك 1").snippet("5593 م2").draggable(true));
        LatLng mole2 = new LatLng(24.201297, 32.842566); // مول تجاري بالحي الثاني منطقة ابني بيتك 1
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubblechartreuse_73025)).position(mole2).title("  مول تجاري بالحي الثاني منطقة ابني بيتك 1 ").snippet("5593 م2").draggable(true));
        LatLng urhouse = new LatLng(24.201550, 32.843599); // ابني يتك 1
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkerinsideazure_7304)).position(urhouse).title("  ابني يتك 1 ").snippet("5593 م2").draggable(true));
        LatLng trafficaps = new LatLng(24.201858, 32.839347); // مبني إدارة مرور اسوان الجديدة
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkerinsideazure_7304)).position(trafficaps).title(" مبني إدارة مرور اسوان الجديدة ").snippet("5593 م2").draggable(true));
        LatLng azheer = new LatLng(24.206332, 32.841595); // المعهد الازهري بالحي الثاني
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubblechartreuse_73025)).position(azheer).title(" المعهد الازهري بالحي الثاني ").snippet("5593 م2").draggable(true));
        LatLng schoolnile = new LatLng(24.207426, 32.845513); // مدرسة النيل
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubblechartreuse_73025)).position(schoolnile).title(" مدرسة النيل ").snippet("5593 م2").draggable(true));

       // LatLng homeis = new LatLng();
        CircleOptions circleOptions = new CircleOptions()
                .center(sydney) // مبني الجهاز
                .radius(50)
                .strokeWidth(2).strokeColor(Color.RED).clickable(true)
                .fillColor(Color.parseColor("#500084d3"));
        CircleOptions hoc = new CircleOptions()
                .center(cornash) // الشريط النهري
                .radius(600)
                .strokeWidth(2).strokeColor(Color.BLUE).clickable(true)
                .fillColor(Color.parseColor("#500084d3"));
        CircleOptions elky = new CircleOptions()
                .center(elk) // محطةة المحولات
                .radius(120)
                .strokeWidth(2).strokeColor(Color.GREEN).clickable(true)
                .fillColor(Color.parseColor("#500084d3"));
        CircleOptions watry = new CircleOptions()
                .center(watr) // محطة مياه الشرب
                .radius(100)
                .strokeWidth(2).strokeColor(Color.GREEN).clickable(true)
                .fillColor(Color.parseColor("#500084d3"));
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.
                        fromResource(R.mipmap.if_mapmarkermarkeroutsideazure_73051))
                .position(sydney).title("مبني جهاز مدينة أسوان الجديدة")
                .snippet("مدينة أسوان الجديدة").draggable(true));
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.
                        fromResource(R.mipmap.if_mapmarkermarkeroutsideazure_73051))
                .position(uniteheeling).title("الوحدة العلاجية")
                .snippet("الوحدة العلاجية").draggable(true));
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.if_mapmarkermarkeroutsideazure_73051)).
                position(cornash)
                .title("الشريط النهري")
                .snippet("الشريط السياحي").draggable(true));

        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.mapsydne))
                .position(sydney, 50f, 50f);

    //    mMap.addGroundOverlay(newarkMap).setTag("asdfsda");
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsideazure_73051)).position(ho).title("الكوبر المعلق").draggable(true));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsideazure_73051)).position(elk).title("موقع محطة المحولات").draggable(true));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsideazure_73051)).position(watr).title("موقع محطة مياه الشرب ").draggable(true));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsideazure_73051)).position(univ).title("جامعة اسوان").draggable(true));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsideazure_73051)).position(gas).title(" محطة البنزين").draggable(true));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkermarkeroutsideazure_73051)).position(langschool).title(" مدرسة لغات").draggable(true));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubblepink_7)).position(masjed).title(" المسجد الجامع").snippet("11186 م2 ").draggable(true));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubblepink_7)).position(school1).title(" مدرسة اعدادي ").snippet("7195 م2").draggable(true));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubblepink_7)).position(school2).title(" مدرسة ابتدائي ").snippet("5593 م2").draggable(true));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.if_mapmarkerbubblepink_7)).position(masjed2).title(" المسجد الجامع ").snippet("5593 م2").draggable(true));
        mMap.addCircle(hoc);
        mMap.addCircle(circleOptions);
        mMap.addCircle(elky);
        mMap.addCircle(watry);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 9000, null);
        mMap.setMapType(mMap.MAP_TYPE_SATELLITE); // Here is where you set the map type
        mMap.setBuildingsEnabled(false);
        mMap.setIndoorEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, " ", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onLocationChanged(Location location) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
            return;
        }

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    public void onProviderEnabled(String provider) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }


    }
    @Override
    public void onProviderDisabled(String provider) {
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
       getCurrentLocation();
    }
    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        Toast.makeText(this,"موقع",Toast.LENGTH_SHORT);
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(MapsActivity.this, "onMarkerClick", Toast.LENGTH_SHORT).show();
        return true;
    }
    @Override
    public void onMarkerDragStart(Marker marker) {
        Toast.makeText(MapsActivity.this, "onMarkerDragStart", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onMarkerDrag(Marker marker) {
        Toast.makeText(MapsActivity.this, "onMarkerDrag", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onMarkerDragEnd(Marker marker) {
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //move to current position
        moveMap();
    }
    @Override
    public void onClick(View v) {
        Log.v(TAG,"view click event");
        if(v == fabAdd){
            LatLng univ = new LatLng(24.168175, 32.844843); // جامعة اسوان
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(univ, 15));
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 9000, null);
            CircleOptions circleOptions = new CircleOptions()
                    .center(univ) // جامعة اسوان
                    .radius(100)
                    .strokeWidth(3).strokeColor(Color.RED).clickable(true)
                    .fillColor(Color.parseColor("#500084d3"));
            mMap.addCircle(circleOptions);
        }else if (v == fabDelete){
            LatLng langschool = new LatLng(24.193565, 32.845299); // مدرسة لغات

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(langschool, 15));
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 9000, null);
        }else if (v == fabgas){
            LatLng gas = new LatLng(24.180548, 32.850599); // محطة البنزين
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gas, 15));
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 9000, null);
        }else if(v == fabuniteheeling){
            LatLng uniteheeling = new LatLng(24.189810, 32.850256); // الوحدة العلاجية
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uniteheeling, 15));
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 9000, null);
        }else if (v == fabEdit){
            LatLng sydney = new LatLng(24.1896670, 32.8437700); // مبني جهاز المدينة
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 9000, null);
        }
    }
    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();

    }
    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }
    private void getCurrentLocation() {
      //  mMap.clear();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
            moveMap();
        }
    }
    private void moveMap() {
        /**
         * Creating the latlng object to store lat, long coordinates
         * adding marker to map
         * move the camera with animation
         */
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title(" "));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.getUiSettings().setZoomControlsEnabled(true);


    }
}