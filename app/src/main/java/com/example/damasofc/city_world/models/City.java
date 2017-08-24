package com.example.damasofc.city_world.models;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import com.example.damasofc.city_world.App.MyApplication;

import java.net.URI;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by damasofc on 08-19-17.
 */

public class City extends RealmObject{
    @PrimaryKey
    private int id;
    @Required
    private String imgCity;
    @Required
    private String nameCity;
    @Required
    private String description;

    private float rating;

    public City(){

    }

    public City(String imgCity, String nameCity, String description, float rating) {
        this.id = MyApplication.cityId.incrementAndGet();
        this.imgCity = imgCity;
        this.nameCity = nameCity;
        this.description = description;
        this.rating = rating;
    }

    public String getImgCity() {
        return imgCity;
    }

    public void setImgCity(String imgCity) {
        this.imgCity = imgCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
