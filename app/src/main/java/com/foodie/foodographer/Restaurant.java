package com.foodie.foodographer;
import android.os.Parcelable;
import android.os.Parcel;
// http://sohailaziz05.blogspot.com/2012/04/passing-custom-objects-between-android.html
public class Restaurant implements Parcelable{
    private String name;
    private String IMGURL;
    private float rating;
    private String location;

    public Restaurant(String name, String IMGURL, float rating, String location){
        this.name = name;
        this.IMGURL = IMGURL;
        this.rating = rating;
        this.location = location;
    }
    public Restaurant(Parcel in){
        String[] data = new String[4];
        in.readStringArray(data);
        this.name=data[0];
        this.IMGURL=data[1];
        this.rating=Float.parseFloat(data[2]);
        this.location=data[3];
    }

    public String getName(){
        return this.name;
    }
    public String getIMGURL(){
        return this.IMGURL;
    }
    public float getRating(){return this.rating;}
    public String getLocation() { return this.location; }

    @Override
    public int describeContents() {
        //  TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub

        dest.writeStringArray(new String[]{this.name,this.IMGURL,String.valueOf(this.rating), this.location});
    }

    public static final Parcelable.Creator<Restaurant> CREATOR= new Parcelable.Creator<Restaurant>() {

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
