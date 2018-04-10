package com.newaswan.seven;

/**
 * Created by Anonymo on 12/6/2017.
 */

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {
    public String userId;
    public String name;
    public String id;
    public String email;
    public String namo;
    public String land;
    public String chat;



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userId,String name,String id, String email,String namo,String land,String chat) {
        this.userId = userId;
        this.name = name;
        this.id = id;
        this.email = email;
        this.namo = namo;
        this.land = land;
        this.chat = chat;
    }

}