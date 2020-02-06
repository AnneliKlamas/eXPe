package com.example.expe.dao;

import com.example.expe.data.Profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class profileDAO implements Serializable {

    List<Profile> profiles;

    public profileDAO() {
        this.profiles = new ArrayList<>();
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }


    public void addProfile(Profile profile) {
        this.profiles.add(profile);
    }


}
