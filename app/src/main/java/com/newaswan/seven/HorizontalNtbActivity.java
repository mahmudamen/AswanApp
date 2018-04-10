package com.newaswan.seven;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
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
import java.util.Random;

import androidmads.updatehandler.app.UpdateHandler;
import at.markushi.ui.CircleButton;
import devlight.io.library.ntb.NavigationTabBar;
import me.leolin.shortcutbadger.ShortcutBadger;

public class HorizontalNtbActivity extends AppCompatActivity implements  View.OnClickListener {
    private FloatingActionMenu fam;
    private FloatingActionButton fabEdit, fabDelete, fabAdd;
    private ToolTipView myToolTipView;
    private FirebaseAuth firebaseAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    FirebaseDatabase database;
    DatabaseReference myRef ;
    List<PhotoModel> list;
    List<PhotoModel> lists;
    List<PhotoModel> sale;
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
    private static String TAG = "HorizontalNtbActivity";
    Intent intent;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_ntb);
      final  ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
      final   NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
        onStarted();
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
        database = FirebaseDatabase.getInstance();
        Query myRefNews = database.getReference("News").limitToFirst(10).orderByChild("id");
        Query myRefWater = database.getReference("WaterFront").limitToFirst(10).orderByChild("id");
        Query myRefHouse = database.getReference("House").limitToFirst(10).orderByChild("id");
        Query myRefProj = database.getReference("ProjectActivity").limitToFirst(10).orderByChild("id");
        Query myRefSales = database.getReference("SalesActivity").limitToFirst(10).orderByChild("id");
        myRefNews.keepSynced(true);

        myRefNews.addListenerForSingleValueEvent(new ValueEventListener() {
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
        myRefWater.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    lists.add(photo);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });
        myRefHouse.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRefProj.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRefSales.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sale = new ArrayList<PhotoModel>();
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
                    sale.add(photo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        initUI();
        //handling each floating action button clicked
        FabButton();
        fabDelete.setOnClickListener(onButtonClick());
        fabEdit.setOnClickListener(onButtonClick());
        fabAdd.setOnClickListener(onButtonClick());
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){

        }
        else
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                TextView textView =findViewById(R.id.username);
                textView.setText("Welcome "+  usernameFromEmail(user.getEmail()));
        }


    }
    protected void onStart(){
        super.onStart();
        if (isConnected()){
           setAutoUpdate();

        }


    }
    private void initUI() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = LayoutInflater.from(
                        getBaseContext()).inflate(R.layout.item_vp, null, false);


                final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(
                                getBaseContext(), LinearLayoutManager.VERTICAL, false ));

                    if(position == 0 ){
                        MainAdapter recyclerAdapter = new MainAdapter(sale, HorizontalNtbActivity.this);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(recyclerAdapter);
                        container.addView(view);
                        return view;
                    }else if(position == 1){
                        boolean success = ShortcutBadger.removeCount(HorizontalNtbActivity.this);
                        MainAdapter recyclerAdapter = new MainAdapter(proj, HorizontalNtbActivity.this);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(recyclerAdapter);
                        container.addView(view);
                        return view;
                    }else if(position == 2){
                        MainAdapter recyclerAdapter = new MainAdapter(list, HorizontalNtbActivity.this);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(recyclerAdapter);
                        container.addView(view);
                        return view;
                    }else if(position == 3){
                        MainAdapter recyclerAdapter = new MainAdapter(hos, HorizontalNtbActivity.this);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(recyclerAdapter);
                        container.addView(view);
                        return view;
                } else {
                        MainAdapter recyclerAdapter = new MainAdapter(lists, HorizontalNtbActivity.this);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(recyclerAdapter);
                        container.addView(view);
                        return view;
                    }
            }
        });
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_releases_black_48dp),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_new_releases_black_48dp))
                        .title("اعلانات")
                        .badgeTitle("طرح")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_trending_up_black_48dp),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_trending_up_black_48dp))
                        .title("مشروعات")
                        .badgeTitle("متجدد")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home_black_48dp),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_home_black_48dp))
                        .title("اخبار")
                        .badgeTitle("جديدة")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_business_black_48dp),
                        Color.parseColor(colors[3]))
                        .title("الاسكان")
                        .badgeTitle("الاجتماعي")
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_business_black_48dp))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_whatshot_black_48dp),
                        Color.parseColor(colors[4]))
                        .title("السياحي")
                        .badgeTitle("جديد")
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_whatshot_black_48dp))
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 2);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setBgColor(Color.parseColor("#ff9900"));
        navigationTabBar.setIsTitled(true);
        navigationTabBar.setIsTinted(true);
        navigationTabBar.setIsBadgeUseTypeface(true);
        //IMPORTANT: ENABLE SCROLL BEHAVIOUR IN COORDINATOR LAYOUT
        navigationTabBar.setBehaviorEnabled(true);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if(index == 1 ){
                 //   model.notifyAll();



                }
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();



            }
        });

        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();


            }

            @Override
            public void onPageScrollStateChanged(final int state) {
               fam.hideMenu(true);

            }

        });
        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
//                            fam.showMenu(true);

                        }
                    }, i * 100);
                }
            }
        }, 500);

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.parent);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);

                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final String title = String.valueOf(new Random().nextInt(15));
                            if (!model.isBadgeShowed()) {
                                model.setBadgeTitle(title);
                                model.showBadge();

                            } else model.updateBadgeTitle(title); fam.showMenu(true);
                        }
                    }, i * 100);
                }

                coordinatorLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Snackbar snackbar = Snackbar.make(navigationTabBar, "مرحبا بكم في أسوان الجديدة", Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(Color.parseColor("#423752"));
                        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text))
                                .setTextColor(Color.parseColor("#ff9900"));
                        snackbar.show();
                        fam.showMenu(true);
                    }
                }, 1000);
            }
        });

        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#009F90AF"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#3e0074"));
    }
    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(currentNetworkInfo == null){
            return false;
        }else {
            return true;
        }

    }
    @Override
    public void onClick(View v) {

    }
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
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
                  //  loaddate(currentNetworkInfo);
                }
            });
            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
            anim.setInterpolator(new BounceInterpolator());
            anim.setDuration(1000);
            labelbadge1.toggle(anim, null);
            boolean success = ShortcutBadger.removeCount(HorizontalNtbActivity.this);
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
                //    loaddatewater(currentNetworkInfo);
                }
            });
            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
            anim.setInterpolator(new BounceInterpolator());
            anim.setDuration(1000);
            labelbadge2.toggle(anim, null);
            boolean success = ShortcutBadger.removeCount(HorizontalNtbActivity.this);
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
                 //   loadpro(currentNetworkInfo);
                }
            });
            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
            anim.setInterpolator(new BounceInterpolator());
            anim.setDuration(1000);
            labelbadge3.toggle(anim, null);
            boolean success = ShortcutBadger.removeCount(HorizontalNtbActivity.this);

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
                //    loadhos(currentNetworkInfo);
                }
            });
            TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
            anim.setInterpolator(new BounceInterpolator());
            anim.setDuration(1000);
            labelbadge4.toggle(anim, null);
            boolean success = ShortcutBadger.removeCount(HorizontalNtbActivity.this);
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
            boolean success = ShortcutBadger.removeCount(HorizontalNtbActivity.this);

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
        if(currentUser == null){
            return false;
        }else {
            final String theUser = usernameFromEmail(currentUser.getEmail());
            TextView textView =findViewById(R.id.username);
            textView.setText("Wellcome " + theUser);
            String url = "https://seven-1810b.firebaseio.com/users.json";
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {
                    if(doOnSuccess(s,theUser)==true){
                        startService(
                                new Intent(HorizontalNtbActivity.this, BadgeIntentService.class).putExtra("badgeCount", 0)
                        );
                    }
                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println("" + volleyError);
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(HorizontalNtbActivity.this);
            rQueue.add(request);
            return true;
        }




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
              //  chatMess();
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

            HorizontalNtbActivity.AsyncTaskRunner runner = new HorizontalNtbActivity.AsyncTaskRunner();
            runner.execute();
        }

    }
    public void FabButton(){
        fabAdd = (FloatingActionButton) findViewById(R.id.fab2);
        fabDelete = (FloatingActionButton) findViewById(R.id.fab3);
        fabEdit = (FloatingActionButton) findViewById(R.id.fab1);
        fam = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fam.setMenuButtonColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_mov));
        fam.setMenuButtonColorPressed(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange));
        fam.setMenuButtonColorRipple(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange));
        fabAdd.setColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_mov));
        fabAdd.setColorPressed(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange));
        fabAdd.setColorRipple(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange));
        fabDelete.setColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_mov));
        fabDelete.setColorPressed(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange));
        fabDelete.setColorRipple(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange));
        fabEdit.setColorNormal(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_mov));
        fabEdit.setColorPressed(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange));
        fabEdit.setColorRipple(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange));
        fabAdd.setLabelColors(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_mov),ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange),ContextCompat.getColor(getApplicationContext(), R.color.app_primary_mov));
        fabEdit.setLabelColors(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_mov),ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange),ContextCompat.getColor(getApplicationContext(), R.color.app_primary_mov));
        fabDelete.setLabelColors(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_mov),ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange),ContextCompat.getColor(getApplicationContext(), R.color.app_primary_mov));
        fabAdd.setLabelTextColor(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange));
        fabEdit.setLabelTextColor(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange));
        fabDelete.setLabelTextColor(ContextCompat.getColor(getApplicationContext(), R.color.app_primary_orange));
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

        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });


    }

    private View.OnClickListener onButtonClick() {
        final Intent i = new Intent(this,SinginActivity.class);
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
                 //   startActivity(iee);
                }else {

                }
                fam.close(true);
            }
        };
    }
}