package com.example.expe.data;

import java.io.Serializable;

public class Profile implements Serializable {

    private String name;

    private String email;

    private int xp;

    public Profile(String name, String email, int xp) {
        this.name = name;
        this.email = email;
        this.xp = xp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
