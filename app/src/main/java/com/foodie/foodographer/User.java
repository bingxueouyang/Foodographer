package com.foodie.foodographer;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;



public class User implements Parcelable {
    private DatabaseReference userRefer;
    public String IMGURL, email, expert1, expert2, expert3, interest1, interest2, interest3, interest4;

    public User(String email, String IMGURL, String expert1,
                String expert2, String expert3, String interest1, String interest2,
                String interest3, String interest4){
        //this.uid = uid;
        this.email = email;
        this.IMGURL = IMGURL;
        this.expert1 = expert1;
        this.expert2 = expert2;
        this.expert3 = expert3;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.interest3 = interest3;
        this.interest4 = interest4;
    }

    public void setEmail(String email){
        this.email = email;
        Log.i("ssss", email);
    }
    public String getEmail(){
        //Log.i("ssss",this.email);
        return this.email;
    }

    public void setIMGURL(String IMGURL){
        this.IMGURL = IMGURL;
    }
    public String getIMGURL(){
        return this.IMGURL;
    }

    public String getExpert1(){
        return this.expert1;
    }

    public String getExpert2(){
        return this.expert2;
    }

    public String getExpert3(){
        return this.expert3;
    }

    public String getInterest1() { return this.interest1; }

    public String getInterest2() { return this.interest2; }

    public String getInterest3() { return this.interest3; }

    public String getInterest4() { return this.interest4; }

    public User(Parcel in){
        //uid, IMGURL, email, expert1, expert2, expert3, interest1, interest2, interest3, interest4;
        this.email = in.readString();
        this.IMGURL = in.readString();
        this.expert1 = in.readString();
        this.expert2 = in.readString();
        this.expert3 = in.readString();
        this.interest1 = in.readString();
        this.interest2 = in.readString();
        this.interest3 = in.readString();
        this.interest4 = in.readString();
    }

    @Override
    public int describeContents() {
        //  TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(this.email);
        dest.writeString(this.IMGURL);
        dest.writeString(this.expert1);
        dest.writeString(this.expert2);
        dest.writeString(this.expert3);
        dest.writeString(this.interest1);
        dest.writeString(this.interest2);
        dest.writeString(this.interest3);
        dest.writeString(this.interest4);
    }

    public static final Parcelable.Creator<User> CREATOR= new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new User(source);  //using parcelable constructor
        }

        @Override
        public User[] newArray(int size) {
            // TODO Auto-generated method stub
            return new User[size];
        }
    };


}
