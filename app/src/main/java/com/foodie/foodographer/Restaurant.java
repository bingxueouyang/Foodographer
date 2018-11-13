package com.foodie.foodographer;
import android.os.Parcelable;
import com.yelp.fusion.client.models.Business;
import java.util.ArrayList;
import android.os.Parcel;
import com.yelp.fusion.client.models.Business;
// http://sohailaziz05.blogspot.com/2012/04/passing-custom-objects-between-android.html
public class Restaurant implements Parcelable{
    private String id;
    private String name;
    private String IMGURL;
    private float distance;
    private float rating;
    private float chineseRating;
    private float americanRating;
    private float italianRating;
    private float frenchRating;
    private float indianRating;
    private float japaneseRating;
    private float koreanRating;
    private float vietnameseRating;
    //private float expertRating; // rating from user expert in this type of rest if we're running out of time
    private String location;
    //private ArrayList<Review> reviews;

    public Restaurant(Business p){
        this.id = p.getId();
        this.name = p.getName();
        this.rating = (float)p.getRating();
        this.IMGURL = p.getImageUrl();
        this.distance = (float)p.getDistance();

        if(p.getLocation().getAddress2() != "") {
            location = p.getLocation().getAddress1() + ", " + p.getLocation().getAddress2() + ", " + p.getLocation().getCity();
            if (p.getLocation().getAddress3() != "") {
                location = p.getLocation().getAddress1() + ", " + p.getLocation().getAddress2() + ", " + p.getLocation().getAddress3() + ", " + p.getLocation().getCity();
            }
        }
        else {
            location = p.getLocation().getAddress1() + ", " + p.getLocation().getCity();
        }

        // add review here from firebase?

    }
    public String getId() { return this.id; };

    public String getName(){
        return this.name;
    }

    public void setIMGURL(String IMGURL){
        this.IMGURL = IMGURL;
    }
    public String getIMGURL(){
        return this.IMGURL;
    }

    public void setRating(float rating){
        this.rating = rating;
    }
    public float getRating(){
        return this.rating;
    }



    public float getChineseRating() {
        return chineseRating;
    }
    public float getAmericanRating() {
        return americanRating;
    }
    public float getItalianRating(){
        return italianRating;
    }

    public float getJapaneseRating() {
        return japaneseRating;
    }

    public float getKoreanRating() {
        return koreanRating;
    }

    public float getFrenchRating() {
        return frenchRating;
    }

    public float getIndianRating() {
        return indianRating;
    }

    public float getVietnameseRating() {
        return vietnameseRating;
    }

    public void setChineseRating(float chineseRating){this.chineseRating = chineseRating;}

    public void setAmericanRating(float americanRating) {
        this.americanRating = americanRating;
    }

    public void setFrenchRating(float frenchRating) {
        this.frenchRating = frenchRating;
    }

    public void setIndianRating(float indianRating) {
        this.indianRating = indianRating;
    }

    public void setItalianRating(float italianRating) {
        this.italianRating = italianRating;
    }

    public void setJapaneseRating(float japaneseRating) {
        this.japaneseRating = japaneseRating;
    }

    public void setKoreanRating(float koreanRating) {
        this.koreanRating = koreanRating;
    }

    public void setVietnameseRating(float vietnameseRating) {
        this.vietnameseRating = vietnameseRating;
    }

    public String getLocation() {
        return this.location;
    }

    public float getDistance() { return this.distance; }

    /*
    public ArrayList<Review> getReviews(){
        return reviews;
    }

    public void addReview(Review r){
        reviews.add(r);
    }

    public void delReview(String uid){
        //find review by uid?
    }
    */

    public Restaurant(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.IMGURL = in.readString();
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
        dest.writeString(this.IMGURL);
        dest.writeFloat(this.rating);
        dest.writeString(this.location);
        dest.writeFloat(this.distance);
        //dest.writeTypedList(this.reviews);

        dest.writeStringArray(new String[]{this.name,this.IMGURL,String.valueOf(this.rating), this.location, String.valueOf(this.distance) });
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
