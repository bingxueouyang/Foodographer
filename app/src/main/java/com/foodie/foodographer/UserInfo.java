package com.foodie.foodographer;

import java.util.ArrayList;

public class UserInfo {
    public String email;
    public ArrayList<String> expertises, interests;

    public UserInfo(String email, ArrayList<String> expertises, ArrayList<String> interests){
        this.email=email;
        this.expertises=expertises;
        this.interests=interests;
    }
}
