package com.newaswan.seven;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Anonymo on 11/23/2017.
 */
public class NewsActivity extends AppCompatActivity {
    private static View rootCardView;
    private boolean isTouch = false;
    private ViewGroup mainLayout;
    FirebaseDatabase database;
    DatabaseReference myRef ;
    List<PhotoModel> list;
    RecyclerView recycle;
    ImageButton view;
    private ProgressDialog progressDialog;
    Animation fak;
    static boolean isInitialized = false;
    private static String TAG = "NewsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            if(!isInitialized){
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                isInitialized = true;
            }else {
                Log.d(TAG,"Already Initialized");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        setContentView(R.layout.activity_news);
        progressDialog = new ProgressDialog(this);

        final  Toast ms = Toast.makeText(this,"rootcardview",Toast.LENGTH_SHORT);
        final Toast m = Toast.makeText(this,"يتم تحديث البيانات",Toast.LENGTH_SHORT);
        final Toast j = Toast.makeText(this,"تأكد من الاتصال بالانترنت ",Toast.LENGTH_SHORT);
        mainLayout = (RelativeLayout) findViewById(R.id.activity_photo);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        loaddate(currentNetworkInfo);
                        //  break;
                    case MotionEvent.ACTION_MOVE:

                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d("UP", "S");
                        break;
                }
                return false;
            }
        });
        recycle = findViewById(R.id.recycle);
        recycle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //        loaddate(currentNetworkInfo);
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
        fak = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_refresh);
        fak.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                progressDialog.dismiss();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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

                    photo.setId(id);
                    photo.setTitle(title);
                    photo.setShortdesc(shortdesc);
                    photo.setDayx(day);
                    photo.setDate(date);
                    photo.setImage(image);
                    list.add(photo);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });
        final FloatingActionButton fabs = findViewById(R.id.fabs);
        fabs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
                fabs.startAnimation(fak);
                progressDialog.setMessage("يتم تحديث المعلومات الان برجاء الانتظار");
                progressDialog.show();
                loaddate(currentNetworkInfo);
            }
        });
    }
    public void loaddate(NetworkInfo currentNetworkInfo){
        final Toast j = Toast.makeText(this,"تأكد من الاتصال بالانترنت ",Toast.LENGTH_SHORT);
        if(currentNetworkInfo == null ){
            progressDialog.dismiss();
            j.show();
        }else {
            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list, NewsActivity.this);
            RecyclerView.LayoutManager recyce = new GridLayoutManager(NewsActivity.this, 1);
            recycle.setLayoutManager(recyce);
            recycle.setItemAnimator(new DefaultItemAnimator());
            recycle.setAdapter(recyclerAdapter);
            //progressDialog.dismiss();
        }
    }

}