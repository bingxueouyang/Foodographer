package com.foodie.foodographer;
import android.os.Parcelable;
import com.yelp.fusion.client.models.Business;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Parcel;
import com.yelp.fusion.client.models.Category;
import com.yelp.fusion.client.models.Hour;

/*
 Parcelable explanation:
 http://sohailaziz05.blogspot.com/2012/04/passing-custom-objects-between-android.html
 Description:
 Restaurant.java is used to store our custom Restaurant obj in Firebase and display in RecyclerResultList
 The storing action is triggered by the first time it's searched by user
 but reading Restaurant obj is not from here, you should read from Firebase by onDataChange
 Then use the methods inside this class
 */
public class Restaurant implements Parcelable{
    private String id;
    private String name;
    private String imgurl;
    private String phone;
    private String displayPhone;
    private String price;
    private ArrayList<Category> categories;
    private ArrayList<Hour> hours;
    private float distance;
    private float rating;

    // hashmap that stores each experties' review count
    public HashMap<String, Integer> expertCount;
    // hashmap that stores each experties' rating
    public HashMap<String, Float> expertRating;

    //private float expertRating;
    //rating from user expert in this type of rest if we're running out of time
    private String location;
    public Restaurant(){

    }
    public Restaurant(Business p){
        this.id = p.getId();
        this.name = p.getName();
        this.rating = (float)p.getRating();
        this.imgurl = p.getImageUrl();
        this.distance = (float)p.getDistance();
        this.categories = p.getCategories();
        this.hours = p.getHours();
        this.phone = p.getPhone();
        this.displayPhone = p.getDisplayPhone();
        this.price = p.getPrice();
        expertCount = new HashMap<>();
        expertCount.put("chinese", 0);
        expertCount.put("american", 0);
        expertCount.put("italian", 0);
        expertCount.put("french", 0);
        expertCount.put("indian", 0);
        expertCount.put("japanese", 0);
        expertCount.put("korean", 0);
        expertCount.put("vietnamese", 0);

        expertRating = new HashMap<>();
        expertRating.put("chinese", 0.0f);
        expertRating.put("american", 0.0f);
        expertRating.put("italian", 0.0f);
        expertRating.put("french", 0.0f);
        expertRating.put("indian", 0.0f);
        expertRating.put("japanese", 0.0f);
        expertRating.put("korean", 0.0f);
        expertRating.put("vietnamese", 0.0f);

        if(p.getLocation().getAddress2() != "") {
            location = p.getLocation().getAddress1() + ", " +
                    p.getLocation().getAddress2() + ", " + p.getLocation().getCity();
            if (p.getLocation().getAddress3() != "") {
                location = p.getLocation().getAddress1() + ", " + p.getLocation().getAddress2() +
                        ", " + p.getLocation().getAddress3() + ", " + p.getLocation().getCity();
            }
        }
        else {
            location = p.getLocation().getAddress1() + ", " + p.getLocation().getCity();
        }

    }
    public String getId() { return this.id; };

    public String getName(){
        return this.name;
    }
    public String getIMGURL(){
        return this.imgurl;
    }

    public String getPrice() {
        return this.price;
    }

    public void setRating(float rating){
        this.rating = rating;
    }
    public float getRating(){
        return this.rating;
    }

    public String getDisplayPhone() {
        return this.displayPhone;
    }
    public ArrayList<Hour> getHours() {
        return this.hours;
    }
    public String getPhone() {
        return this.phone;
    }
    public ArrayList<Category> getCategories() {
        return this.categories;
    }


    public String getLocation() {
        return this.location;
    }

    public float getDistance() { return this.distance; }

    public Restaurant(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.imgurl = in.readString();
        this.rating = in.readFloat();
        this.location = in.readString();
        this.distance = in.readFloat();
        //this.reviews = in.readParcelable(Review.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        //  TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imgurl);
        dest.writeFloat(this.rating);
        dest.writeString(this.location);
        dest.writeFloat(this.distance);
        //dest.writeTypedList(this.reviews);

        dest.writeStringArray(new String[]{this.name,this.imgurl,
                String.valueOf(this.rating), this.location, String.valueOf(this.distance) });
    }

    public static final Parcelable.Creator<Restaurant> CREATOR=
            new Parcelable.Creator<Restaurant>() {

        @Override
        public Restaurant createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new Restaurant(source);  //using parcelable constructor
        }

        @Override
        public Restaurant[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Restaurant[size];
        }
    };
}