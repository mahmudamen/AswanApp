package com.newaswan.seven;

/**
 * Created by Anonymo on 12/6/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.readystatesoftware.viewbadger.BadgeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Users extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText;
    final static  ArrayList<String> al = new ArrayList<>();
    final static ArrayList<String> vid = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    private FirebaseAuth firebaseAuth;
    static boolean isInitialized = false;
    private static String TAG = "Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

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
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        usersList = (ListView)findViewById(R.id.usersList);


        noUsersText = (TextView)findViewById(R.id.noUsersText);

        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://seven-1810b.firebaseio.com/users.json";


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
        rQueue.add(request);

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);

                UserDetails.username = usernameFromEmail(user.getEmail());
                startActivity(new Intent(Users.this, ChatActivity.class));
            }
        });
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

          //  Iterator i = obj.keys();
            Iterator<String> i = obj.keys();
            String key = "";
            String value = "";


            while(i.hasNext()){
                key = i.next().toString();
                value = obj.getJSONObject(key).getString("chat");
                if(!key.equals(UserDetails.username)) {
                 //   JSONObject xx = new JSONObject(obj.get(key).toString();
                    al.add(key);
                    vid.add(value);



                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
            usersList.setAdapter(new BadgeAdapter(this));

        }

        pd.dismiss();
    }
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }


    private static class BadgeAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context mContext;
        private static final int droidGreen = Color.parseColor("#A4C639");
        Object[] objNames = al.toArray();
        Object[] h = vid.toArray();
        String[] Stringname = Arrays.copyOf(objNames, objNames.length, String[].class);
        String[] vnum = Arrays.copyOf(h,h.length,String[].class);
        public BadgeAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            mContext = context;
        }

        public int getCount() {
            return objNames.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(android.R.layout.simple_list_item_2, null);
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(android.R.id.text1);
                holder.badge = new BadgeView(mContext, holder.text);
                holder.badge.setBadgeBackgroundColor(droidGreen);
                holder.badge.setTextColor(Color.BLACK);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(Stringname[position]);

            if (position >= 0) {
                holder.badge.setText(vnum[position]);
                holder.badge.show();
            } else {
              //  holder.badge.hide();
            }

          //  holder.badge.show();
            return convertView;
        }

        static class ViewHolder {
            TextView text;
            BadgeView badge;
        }
    }

}
