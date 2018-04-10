package com.newaswan.seven;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;
import com.readystatesoftware.viewbadger.BadgeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import androidmads.updatehandler.app.UpdateHandler;
import at.markushi.ui.CircleButton;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener ,  View.OnClickListener {
 //   RelativeLayout rl;z
    private FloatingActionMenu fam;
    private FloatingActionButton fabEdit, fabDelete, fabAdd;
    private ToolTipView myToolTipView;
    private FirebaseAuth firebaseAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    FirebaseDatabase database;
    DatabaseReference myRef ;
    List<PhotoModel> list;
    List<PhotoModel> lists;
    int totalUsers = 0;
    final static  ArrayList<String> al = new ArrayList<>();
    final static ArrayList<String> vid = new ArrayList<>();
    List<PhotoModel> proj,hos ;
    RecyclerView recycle;
    DatabaseReference reference4;
    ImageButton view;
    CircleButton btnAnim1,btnAnim2,btnAnim3,btnAnim4,btnAnim5;
    BadgeView badge1,badge2,badge3,badge4;
    BadgeView labelbadge1,labelbadge2,labelbadge3,labelbadge4,labelbadge5;
    SwipeRefreshLayout swiper;
    private ProgressDialog progressDialog;
    Animation FabRclockwise , FabRanticlockwise ,fabopen , fabclose;
    Switch mySwitch;
    TextView gtitmainRef;
    TextView gcontmainRef;
    ImageView gbg;
    CircleButton button1,button2,button3,button4,button5,button6,button10,button11;
    boolean isOpen = false;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mtitmainRef = mRootRef.child("titmain");
    DatabaseReference getcontmainRef = mRootRef.child("contmain");
    DatabaseReference mbg = mRootRef.child("bgmain");
    int badgeCount = 1;
    static boolean isInitialized = false;
    ToolTipRelativeLayout toolTipRelativeLayout;
    private static String TAG = "MainActivity";
    Intent intent;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onStarted();

     //   final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        try{
            if(!isInitialized){
                Firebase.setAndroidContext(this);
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);

                isInitialized = true;

            }else {
                Log.d(TAG,"Already Initialized");
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
//        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);



      //  CircleButton circleButton = findViewById(R.id.button1);
      //  TextDrawable text= new TextDrawable(this);
      //  text.setText("Hi");
      //  circleButton.setImageDrawable(text);

        fabAdd = (FloatingActionButton) findViewById(R.id.fab2);
        fabDelete = (FloatingActionButton) findViewById(R.id.fab3);
        fabEdit = (FloatingActionButton) findViewById(R.id.fab1);
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

        btnAnim1  =  findViewById(R.id.button1);
        btnAnim2 =  findViewById(R.id.button2);
        btnAnim3 =  findViewById(R.id.button3);
        btnAnim4 =  findViewById(R.id.button4);
        btnAnim5 = findViewById(R.id.button6);

        badge1 = new BadgeView(this, btnAnim1);


        badge2 = new BadgeView(this, btnAnim2);
        badge2.setText("جديد");
        badge2.setBadgePosition(BadgeView.POSITION_CENTER);
     //   badge2.setBadgeMargin(15, 10);
        badge2.setBadgeBackgroundColor(Color.parseColor("#A4C639"));

        badge3 = new BadgeView(this, btnAnim3);
        badge3.setText("جديد");
        badge3.setBadgePosition(BadgeView.POSITION_CENTER);
     //   badge3.setBadgeMargin(15, 10);
        badge3.setBadgeBackgroundColor(Color.parseColor("#A4C639"));

        badge4 = new BadgeView(this, btnAnim4);
        badge4.setText("جديد");
        badge4.setBadgePosition(BadgeView.POSITION_CENTER);
      //  badge4.setBadgeMargin(15, 10);
        badge4.setBadgeBackgroundColor(Color.parseColor("#A4C639"));
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

        //handling each floating action button clicked
        fabDelete.setOnClickListener(onButtonClick());
        fabEdit.setOnClickListener(onButtonClick());
        fabAdd.setOnClickListener(onButtonClick());
        getbudget(1);
        getbudget(2);
        getbudget(3);
        getbudget(4);
        getbudget(5);
        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
           // gtitmainRef.setText("برجاء تسجيل الدخول");
         //   new Handler().postDelayed(new Runnable() {
          //      @Override
           //     public void run() {
             //       addBlueToolTipView();
               // }
           // }, 1500);
            myToolTipView = null;

        }else {

            FirebaseUser user = firebaseAuth.getCurrentUser();
            gtitmainRef.setText("Welcome "+  usernameFromEmail(user.getEmail()));


        }


      //  rl = (RelativeLayout)findViewById(R.id.RelativeLayout1);
        //rl.setBackgroundResource(R.drawable.panbg);

      //  final Toast toast = Toast.makeText(this,"ابداء من هنا ..",Toast.LENGTH_SHORT);
        final StyleableToast toastmap = StyleableToast.makeText(this,"خريطة المدينة",Toast.LENGTH_SHORT,R.style.MyToast);
        final Toast toastfb = Toast.makeText(this,"اخبار المدينة",Toast.LENGTH_SHORT);

        gbg = (ImageView)findViewById(R.id.imageView2);

        fabopen  = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fabclose  = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRclockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_refresh);
        FabRanticlockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recycle = findViewById(R.id.recycle);
        recycle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                          //  loaddate(currentNetworkInfo);
                        break;
                    case MotionEvent.ACTION_MOVE:

                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        database = FirebaseDatabase.getInstance();

        Query myRef = database.getReference("News").orderByChild("id");
        myRef.keepSynced(true);
        final Toast toast = Toast.makeText(this, "تحديث البيانات", Toast.LENGTH_LONG);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list = new ArrayList<PhotoModel>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    PhotoModel value = dataSnapshot1.getValue(PhotoModel.class);
                    PhotoModel photo = new PhotoModel();
                    int id = value.getId();
                    String title = value.getTitle();
                    String shortdesc = value.getShortdesc();
                    String day = value.getDayx();
                    String date = value.getDate();
                    String image = value.getImage();
                    String liq = value.getLiqo();
                    String longdesc = value.getLongdesc();

                    photo.setId(id);
                    photo.setTitle(title);
                    photo.setShortdesc(shortdesc);
                    photo.setDayx(day);
                    photo.setDate(date);
                    photo.setImage(image);
                    photo.setLiqo(liq);
                    photo.setLongdesc(longdesc);
                    list.add(photo);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this,ChatActivity.class);
            this.startActivity(i);
            return true;
        }else if(id ==R.id.action_about ){
            Intent i = new Intent(this,AboutMe.class);
            this.startActivity(i);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
    protected void onStart() {
        super.onStart();

        mbg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tot = dataSnapshot.getValue(String.class);
                Glide.with(getApplicationContext()).load(tot).into(gbg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Query myRef = database.getReference("News").orderByChild("id");
        myRef.keepSynced(true);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list = new ArrayList<PhotoModel>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    PhotoModel value = dataSnapshot1.getValue(PhotoModel.class);
                    PhotoModel photo = new PhotoModel();
                    int id = value.getId();
                    String title = value.getTitle();
                    String shortdesc = value.getShortdesc();
                    String day = value.getDayx();
                    String date = value.getDate();
                    String image = value.getImage();
                    String liq = value.getLiqo();
                    String longdesc = value.getLongdesc();

                    photo.setId(id);
                    photo.setTitle(title);
                    photo.setShortdesc(shortdesc);
                    photo.setDayx(day);
                    photo.setDate(date);
                    photo.setImage(image);
                    photo.setLiqo(liq);
                    photo.setLongdesc(longdesc);
                    list.add(photo);
                    if(photo.id  == 1){
                    //    badgeCount += 1;
                    //    boolean success = ShortcutBadger.applyCount(MainActivity.this,badgeCount);
                        badge1.setText("جديد");
                        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
                        badge1.setBadgeMargin(0, 0);
                        badge1.setBadgeBackgroundColor(Color.RED);

                        TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
                        anim.setInterpolator(new BounceInterpolator());
                        anim.setDuration(1000);
                        badge1.toggle(anim, null);
                    }
                }
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
                recycle.setVisibility(View.VISIBLE);
                loaddate(currentNetworkInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent i = new Intent(this,NewsActivity.class);
            this.startActivity(i);
            return true;
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(this,PhotoActivity.class);
            this.startActivity(i);
            return true;
        } else if (id == R.id.nav_waterfront) {
            Intent i = new Intent(this,WaterFront.class);
            this.startActivity(i);
        } else if (id == R.id.nav_newss) {
            Intent i = new Intent(this, SalesActivity.class);
            this.startActivity(i);
        }else if (id == R.id.nav_house){
            Intent i = new Intent(this,HouseActivity.class);
            this.startActivity(i);
            return true;
        }else if (id == R.id.nav_pro){
            Intent i = new Intent(this,ProjectActivity.class);
            this.startActivity(i);
            return true;
        } else if (id == R.id.nav_fb) {
            Intent i = new Intent(this,Main4Activity.class);
            this.startActivity(i);
            return true;
        } else if (id == R.id.nav_map) {
            Intent i = new Intent(this,MapsActivity.class);
            this.startActivity(i);
            return true;
        } else if(id == R.id.nav_login){
            Intent i = new Intent(this,SinginActivity.class);
            this.startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private View.OnClickListener onButtonClick() {
        final Intent i = new Intent(this,SalesActivity.class);
        final Intent ie = new Intent(this,MapsActivity.class);
        final Intent iee = new Intent(this,ChatActivity.class);
        final Intent bnew = new Intent(this,NewsActivity.class);
        final StyleableToast toastmap = StyleableToast.makeText(this,"خريطة المدينة",Toast.LENGTH_SHORT,R.style.MyToast);
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fabAdd) {
                    //   showToast("Button Add clicked");
                    toastmap.show();
                    startActivity(ie);

                } else if (view == fabDelete) {
                //    showToast("Button Delete clicked");
                    startActivity(i);

                } else if(view == fabEdit) {
                //    showToast("Button Edit clicked");
                    startActivity(iee);
                }else {

                }
                fam.close(true);
            }
        };
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
  //      final Button button1 = (findViewById(R.id.button1));
  //      final Button button2 = (findViewById(R.id.button2));
  //      final Button button3 = (findViewById(R.id.button3));
  //      final Button button4 = (findViewById(R.id.button4));
//        final Button button5 = (findViewById(R.id.button5));
 //       final Button button6 = (findViewById(R.id.button6));

        Drawable dr = getResources().getDrawable(R.drawable.colorcha);
        Drawable dx = getResources().getDrawable(R.drawable.colornone);
        dr.setColorFilter(Color.parseColor("#A4C9E7"), PorterDuff.Mode.SRC_ATOP);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
        final StyleableToast toastmap = StyleableToast.makeText(this,"خريطة المدينة",Toast.LENGTH_LONG,R.style.MyToast);
        final StyleableToast toastnews = StyleableToast.makeText(this,"تم اخفاء الاخبار ",Toast.LENGTH_LONG,R.style.MyToast);
        final StyleableToast toastchat = StyleableToast.makeText(this,"للمقترحات والشكاوي ",Toast.LENGTH_LONG,R.style.MyToast);


        if(id == R.id.button1){
          //  getbudget(1);
            recycle.setVisibility(View.VISIBLE);
            loaddate(currentNetworkInfo);


//           button1.setBackgroundDrawable(dx);

        }
        else if (id == R.id.button2){
          //  getbudget(2);
            recycle.setVisibility(View.VISIBLE);
            loaddatewater(currentNetworkInfo);
        //    button2.setBackgroundDrawable(dx);
        }
        else if(id == R.id.button3){
          //  getbudget(3);
            recycle.setVisibility(View.VISIBLE);
            loadpro(currentNetworkInfo);
         //   button3.setBackgroundDrawable(dx);
        }
        else if(id == R.id.button4){
          //  getbudget(4);
            recycle.setVisibility(View.VISIBLE);
            loadhos(currentNetworkInfo);
           // button4.setBackgroundDrawable(dx);
        }
        else if(id == R.id.button5){
         //   button5.setBackgroundDrawable(dr);
         //  button2.setBackgroundDrawable(dx);
           // button3.setBackgroundDrawable(dx);
          //  button4.setBackgroundDrawable(dx);
         //  button1.setBackgroundDrawable(dx);
         //   button6.setBackgroundDrawable(dx);

            recycle.setVisibility(View.INVISIBLE);
         //   button5.setBackgroundDrawable(dx);
        }
        else if(id == R.id.button6){
            //getbudget(5);
            toastmap.show();
            Intent i = new Intent(this,MapsActivity.class);
            this.startActivity(i);
        }


    }
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
    public void loaddate(NetworkInfo currentNetworkInfo){
        final Toast j = Toast.makeText(this,"تأكد من الاتصال بالانترنت ",Toast.LENGTH_SHORT);
        final Toast x = Toast.makeText(this,"جاري تحميل البيانات",Toast.LENGTH_LONG);
        if(currentNetworkInfo == null ){

            j.show();
        }else {
            x.show();
            MainAdapter recyclerAdapter = new MainAdapter(list, MainActivity.this);
            RecyclerView.LayoutManager recyce = new GridLayoutManager(MainActivity.this, 1);
            recycle.setLayoutManager(recyce);
            recycle.setItemAnimator(new DefaultItemAnimator());
            recycle.setAdapter(recyclerAdapter);
            //progressDialog.dismiss();
        }
    }
    public void loaddatewater(NetworkInfo currentNetworkInfo){
        final Toast j = Toast.makeText(this,"تأكد من الاتصال بالانترنت ",Toast.LENGTH_SHORT);
        final Toast x = Toast.makeText(this,"جاري تحميل البيانات",Toast.LENGTH_LONG);
        if(currentNetworkInfo == null ){

            j.show();
        }else {
            x.show();
            database = FirebaseDatabase.getInstance();

            Query myRef = database.getReference("WaterFront").orderByChild("id");
            myRef.keepSynced(true);
            final Toast toast = Toast.makeText(this, "تحديث البيانات", Toast.LENGTH_LONG);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    lists = new ArrayList<PhotoModel>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        PhotoModel value = dataSnapshot1.getValue(PhotoModel.class);
                        PhotoModel photo = new PhotoModel();
                        int id = value.getId();
                        String title = value.getTitle();
                        String shortdesc = value.getShortdesc();
                        String day = value.getDayx();
                        String date = value.getDate();
                        String image = value.getImage();

                        photo.setId(id);
                        photo.setTitle(title);
                        photo.setShortdesc(shortdesc);
                        photo.setDayx(day);
                        photo.setDate(date);
                        photo.setImage(image);
                        lists.add(photo);
                        if(photo.id == 1){

                            badge2.setText("جديد");
                            badge2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
                            badge2.setBadgeMargin(0, 0);
                            badge2.setBadgeBackgroundColor(Color.RED);


                            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
                            anim.setInterpolator(new BounceInterpolator());
                            anim.setDuration(1000);
                            badge2.toggle(anim, null);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });

            WaterAdapter waterFrontAdapter = new WaterAdapter(lists, MainActivity.this );
            RecyclerView.LayoutManager recyce = new GridLayoutManager(MainActivity.this, 1);
            recycle.setLayoutManager(recyce);
            recycle.setItemAnimator(new DefaultItemAnimator());
            recycle.setAdapter(waterFrontAdapter);
            //progressDialog.dismiss();
        }
    }
    public void loadpro(NetworkInfo currentNetworkInfo){
        final Toast j = Toast.makeText(this,"تأكد من الاتصال بالانترنت ",Toast.LENGTH_SHORT);
        if(currentNetworkInfo == null ){

            j.show();
        }else {

            database = FirebaseDatabase.getInstance();

            Query myRef = database.getReference("ProjectActivity").orderByChild("id");
            myRef.keepSynced(true);
            final Toast toast = Toast.makeText(this, "تحديث البيانات", Toast.LENGTH_LONG);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    proj = new ArrayList<PhotoModel>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        PhotoModel value = dataSnapshot1.getValue(PhotoModel.class);
                        PhotoModel photo = new PhotoModel();
                        int id = value.getId();
                        String title = value.getTitle();
                        String shortdesc = value.getShortdesc();
                        String day = value.getDayx();
                        String date = value.getDate();
                        String image = value.getImage();

                        photo.setId(id);
                        photo.setTitle(title);
                        photo.setShortdesc(shortdesc);
                        photo.setDayx(day);
                        photo.setDate(date);
                        photo.setImage(image);
                        proj.add(photo);
                        if(photo.id == 1){

                            badge3.setText("جديد");
                            badge3.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
                            badge3.setBadgeMargin(0, 0);
                            badge3.setBadgeBackgroundColor(Color.RED);

                            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
                            anim.setInterpolator(new BounceInterpolator());
                            anim.setDuration(1000);
                            badge3.toggle(anim, null);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });

            ProAdapter proAdapter = new ProAdapter(proj, MainActivity.this );
            RecyclerView.LayoutManager recyce = new GridLayoutManager(MainActivity.this, 1);
            recycle.setLayoutManager(recyce);
            recycle.setItemAnimator(new DefaultItemAnimator());
            recycle.setAdapter(proAdapter);
            //progressDialog.dismiss();
        }
    }
    public void loadhos(NetworkInfo currentNetworkInfo){
        final Toast j = Toast.makeText(this,"تأكد من الاتصال بالانترنت ",Toast.LENGTH_SHORT);
        final Toast x = Toast.makeText(this,"جاري تحميل البيانات",Toast.LENGTH_LONG);
        if(currentNetworkInfo == null ){

            j.show();
        }else {
            x.show();
            database = FirebaseDatabase.getInstance();

            Query myRef = database.getReference("House").orderByChild("id");
            myRef.keepSynced(true);
            final Toast toast = Toast.makeText(this, "تحديث البيانات", Toast.LENGTH_LONG);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    hos = new ArrayList<PhotoModel>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        PhotoModel value = dataSnapshot1.getValue(PhotoModel.class);
                        PhotoModel photo = new PhotoModel();
                        int id = value.getId();
                        String title = value.getTitle();
                        String shortdesc = value.getShortdesc();
                        String day = value.getDayx();
                        String date = value.getDate();
                        String image = value.getImage();

                        photo.setId(id);
                        photo.setTitle(title);
                        photo.setShortdesc(shortdesc);
                        photo.setDayx(day);
                        photo.setDate(date);
                        photo.setImage(image);
                        hos.add(photo);
                        if(photo.id == 1){

                            badge4.setText("جديد");
                            badge4.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
                            badge4.setBadgeMargin(0, 0);
                            badge4.setBadgeBackgroundColor(Color.RED);

                            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
                            anim.setInterpolator(new BounceInterpolator());
                            anim.setDuration(1000);
                            badge4.toggle(anim, null);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });

            HosAdapter hosAdapter = new HosAdapter(hos, MainActivity.this );
            RecyclerView.LayoutManager recyce = new GridLayoutManager(MainActivity.this, 1);
            recycle.setLayoutManager(recyce);
            recycle.setItemAnimator(new DefaultItemAnimator());
            recycle.setAdapter(hosAdapter);
            //progressDialog.dismiss();
        }
    }
    public void getbudget(int id){
     //   labelbadge1,labelbadge2,labelbadge3,labelbadge4,labelbadge5;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
       final NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (id == 1 ) {
            btnAnim1 = findViewById(R.id.button1);
            labelbadge1 = new BadgeView(this, btnAnim1);
            labelbadge1.setText("اخبار !!");
            labelbadge1.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
            labelbadge1.setBadgeMargin(0, 0);
           // labelbadge1.setBadgeBackgroundColor(Color.parseColor("#A4C9E7"));
            labelbadge1.setTextColor(Color.BLUE);
            labelbadge1.setBadgeBackgroundColor(Color.YELLOW);
            labelbadge1.setTextSize(12);
            btnAnim1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
                    anim.setInterpolator(new BounceInterpolator());
                    anim.setDuration(1000);
                    labelbadge1.toggle(anim, null);
                    recycle.setVisibility(View.VISIBLE);
                    loaddate(currentNetworkInfo);
                }
            });
            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
            anim.setInterpolator(new BounceInterpolator());
            anim.setDuration(1000);
            labelbadge1.toggle(anim, null);
            boolean success = ShortcutBadger.removeCount(MainActivity.this);
        }else if (id == 2 ){
            btnAnim2 = findViewById(R.id.button2);
            labelbadge2 = new BadgeView(this, btnAnim2);
            labelbadge2.setText("الشريط النهري");
            labelbadge2.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
            labelbadge2.setBadgeMargin(0, 0);
       //     labelbadge2.setBadgeBackgroundColor(Color.parseColor("#A4C9E7"));
            labelbadge2.setTextColor(Color.BLUE);
            labelbadge2.setBadgeBackgroundColor(Color.YELLOW);
            labelbadge2.setTextSize(9);
            btnAnim2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
                    anim.setInterpolator(new BounceInterpolator());
                    anim.setDuration(1000);
                    labelbadge2.toggle(anim, null);
                    recycle.setVisibility(View.VISIBLE);
                    loaddatewater(currentNetworkInfo);
                }
            });
            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
            anim.setInterpolator(new BounceInterpolator());
            anim.setDuration(1000);
            labelbadge2.toggle(anim, null);
            boolean success = ShortcutBadger.removeCount(MainActivity.this);
        }
        else if (id == 3 ){
            btnAnim3 = findViewById(R.id.button3);
            labelbadge3 = new BadgeView(this, btnAnim3);
            labelbadge3.setText("مشروعات");
            labelbadge3.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
            labelbadge3.setBadgeMargin(0, 0);
            labelbadge3.setTextColor(Color.BLUE);
            labelbadge3.setBadgeBackgroundColor(Color.YELLOW);
            labelbadge3.setTextSize(11);
            btnAnim3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
                    anim.setInterpolator(new BounceInterpolator());
                    anim.setDuration(1000);
                    labelbadge3.toggle(anim, null);
                    recycle.setVisibility(View.VISIBLE);
                    loadpro(currentNetworkInfo);
                }
            });
            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
            anim.setInterpolator(new BounceInterpolator());
            anim.setDuration(1000);
            labelbadge3.toggle(anim, null);
            boolean success = ShortcutBadger.removeCount(MainActivity.this);

        }
        else if (id == 4 ){
            btnAnim4 = findViewById(R.id.button4);
            labelbadge4 = new BadgeView(this, btnAnim4);
            labelbadge4.setText("الاسكان");
            labelbadge4.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
            labelbadge4.setBadgeMargin(0, 0);
            labelbadge4.setTextColor(Color.BLUE);
            labelbadge4.setBadgeBackgroundColor(Color.YELLOW);
            labelbadge4.setTextSize(10);
            btnAnim4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
                    anim.setInterpolator(new BounceInterpolator());
                    anim.setDuration(1000);
                    labelbadge4.toggle(anim, null);
                    recycle.setVisibility(View.VISIBLE);
                    loadhos(currentNetworkInfo);
                }
            });
            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
            anim.setInterpolator(new BounceInterpolator());
            anim.setDuration(1000);
            labelbadge4.toggle(anim, null);
            boolean success = ShortcutBadger.removeCount(MainActivity.this);
        }
        else if (id == 5 ){
            btnAnim5 = findViewById(R.id.button6);
            labelbadge5 = new BadgeView(this, btnAnim5);
            labelbadge5.setText("الخريطة");
            labelbadge5.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
            labelbadge5.setBadgeMargin(0, 0);
            labelbadge5.setTextColor(Color.BLUE);
            labelbadge5.setBadgeBackgroundColor(Color.YELLOW);
            labelbadge5.setTextSize(10);
            final Intent i = new Intent(this,MapsActivity.class);
            btnAnim5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
                    anim.setInterpolator(new BounceInterpolator());
                    anim.setDuration(1000);
                    labelbadge5.toggle(anim, null);
                    startActivity(i);

                }
            });
            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
            anim.setInterpolator(new BounceInterpolator());
            anim.setDuration(1000);
            labelbadge5.toggle(anim, null);
            boolean success = ShortcutBadger.removeCount(MainActivity.this);

        }

    }
    private void setAutoUpdate() {
        new UpdateHandler.Builder(this)
                .setContent("اصدار جديد")// Alert Dialog Content
                .setTitle("تحديث البرنامج") //Alert Dialog Text
                .setUpdateText("Yes")
                .setCancelText("No")
                .showDefaultAlert(true)
                .showWhatsNew(true) //Show whats new from play store
                .setCheckerCount(2)
                .setOnUpdateFoundLister(new UpdateHandler.Builder.UpdateListener() {
                    @Override
                    public void onUpdateFound(boolean newVersion, String whatsNew) {

                    }
                })
                .setOnUpdateClickLister(new UpdateHandler.Builder.UpdateClickListener() {
                    @Override
                    public void onUpdateClick(boolean newVersion, String whatsNew) {
                        //Takes user to play store
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                    }
                })
                .setOnCancelClickLister(new UpdateHandler.Builder.UpdateCancelListener() {
                    @Override
                    public void onCancelClick() {
                        // Closes the App
                        finishAffinity();
                    }
                })
                .build();
    }
    private boolean chatMess (){
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        final String theUser = usernameFromEmail(currentUser.getEmail());


        String url = "https://seven-1810b.firebaseio.com/users.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                if(doOnSuccess(s,theUser)==true){
                    startService(
                            new Intent(MainActivity.this, BadgeIntentService.class).putExtra("badgeCount", 0)
                    );
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
        rQueue.add(request);


        return true;
    }
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
        @Override
        protected String doInBackground(String... strings) {
            int i;
            for (i = 0; i <= 100; i += 5) {
                // Sets the progress indicator completion percentage

                try {

                    // Sleep for 5 seconds
                    Thread.sleep(2 * 100000);
                } catch (InterruptedException e) {
                    Log.d("TAG", "sleep failure");
                }
            }
            return "";
        }
        protected void onPreExecute() {
            //Setup precondition to execute some task
            super.onPreExecute();
            if(currentNetworkInfo == null ){


            }else {
                chatMess();
            }
        }
    }
    public boolean doOnSuccess(String s,String theUser){
        try {
            JSONObject obj = new JSONObject(s);
            obj.has(theUser);
            //  Iterator i = obj.keys();
            Iterator<String> i = obj.keys();
            String key = "";
            String value = "";
            int m = 0;


            while(i.hasNext()){
                key = i.next().toString();
                value = obj.getJSONObject(key).getString("chat");
                if(key.equals(theUser)) {
                    //   JSONObject xx = new JSONObject(obj.get(key).toString();
                    al.add(key);
                    vid.add(value);
                    m = Integer.parseInt(value);
                    Object[] objNames = al.toArray();
                    Object[] h = vid.toArray();
                    String[] Stringname = Arrays.copyOf(objNames, objNames.length, String[].class);
                    String[] vnum = Arrays.copyOf(h,h.length,String[].class);
                    if(m > 0){
                        break;

                    }else {
                        return false;
                    }
                }
                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    private void onStarted(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(currentNetworkInfo == null){

        }else{
            setAutoUpdate();

            //  startService(new Intent(MainActivity.this,FirebaseBackgroundService.class));
            Intent serviceIntent  = new Intent("com.newaswan.seven");
            serviceIntent.setPackage(".FirebaseBackgroundService");
            //    startService(serviceIntent);
            //  startService(new Intent(FirebaseBackgroundService.class.getName()));
            //  serviceIntent.setPackage("");

            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute();
        }

    }


}
