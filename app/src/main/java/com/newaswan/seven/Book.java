package com.newaswan.seven;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Anonymo on 21/01/2018.
 */

public class Book implements Parcelable {
    // book basics
    private String title;
    private String shortdesc;
    private String img ;
    private String price;
    private String rating;
    private String liqo;
    private String longdesc;

    protected Book(Parcel in) {
        title = in.readString();
        shortdesc = in.readString();
        img = in.readString();
        price = in.readString();
        rating = in.readString();
        liqo = in.readString();
        longdesc = in.readString();
    }
    public String getTitle() { return title; }
    public String getShortdesc() { return shortdesc; }
    public String getRating() { return rating; }
    public String getImg() { return img; }
    public String getPrice() { return price; }
    public String getLiqo() { return liqo; }
    public String getLongdesc() { return longdesc; }
public Book(String title , String shortdesc,String img,String price,String rating,String liqo,String longdesc){
    this.title = title;
    this.shortdesc = shortdesc;
    this.img = img;
    this.price = price;
    this.rating = rating;
    this.liqo = liqo;
    this.longdesc = longdesc;
}

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(shortdesc);
        parcel.writeString(img);
        parcel.writeString(rating);
        parcel.writeString(price);
        parcel.writeString(liqo);
        parcel.writeString(longdesc);
    }
}