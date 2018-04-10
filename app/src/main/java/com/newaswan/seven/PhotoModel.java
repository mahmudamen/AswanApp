package com.newaswan.seven;

import java.io.Serializable;

/**
 * Created by Anonymo on 11/23/2017.
 */
public class PhotoModel implements Serializable {
    private static final long serialVersionUID = 1L;
    public int id;
    public String title;
    public String shortdesc;
    public String date;
    public String dayx;
    public String image;
    public String overflow;
    public String liqo;
    public String longdesc;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setLiqo(String liqo){this.liqo = liqo;}
    public String getLiqo(){return liqo;}

    public void setLongdesc(String longdesc){this.longdesc = longdesc;}
    public String getLongdesc(){return longdesc;}

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getDayx() {
        return dayx;
    }

    public void setDayx(String dayx) {
        this.dayx = dayx;
    }

    public String getImage() {
        return image;
    }
    public String getOverflow(){
        return overflow;
    }
    public void setOverflow(String overflow){
        this.overflow = overflow;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
