package com.newaswan.seven;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class FirebaseBackgroundService extends Service {

    int totalUsers = 0;
    final static ArrayList<String> al = new ArrayList<>();
    final static ArrayList<String> vid = new ArrayList<>();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    final String theUser = usernameFromEmail(currentUser.getEmail());
    String url = "https://seven-1810b.firebaseio.com/users.json";

    private ValueEventListener handler;
    int badgeCount = 0 ;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

         //   query.addValueEventListener(handler);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                if(doOnSuccess(s,theUser)==true){
                    startService(new Intent(FirebaseBackgroundService.this, BadgeIntentService.class).putExtra("badgeCount", 0));

                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(FirebaseBackgroundService.this);
        rQueue.add(request);
    }
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
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

}