package com.example.dell.search;

import android.graphics.Bitmap;
import android.support.annotation.VisibleForTesting;

import httpClient.webPic;

/**
 * Created by dell on 2018/4/12.
 */

public class Recipe {
    private String name;
    private String path;
    private Bitmap image;
    private String foodId;
    public Recipe(String foodId,String name,String path)
    {
        this.foodId = foodId;
        this.name = name;
        this.path = path;
        image = webPic.ShowPic(path);
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void setFoodId(String foodId){
        this.foodId = foodId;
    }
    public String getPath()
    {
        return path;
    }
    public String getName()
    {
        return name;
    }
    public String getFoodId(){
        return foodId;
    }
    public void setImage(String path)
    {
        image = webPic.ShowPic(path);
    }
    public Bitmap getImage()
    {
        return image;

    }
}
