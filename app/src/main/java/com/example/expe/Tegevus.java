package com.example.expe;

public class Tegevus {
    private String title;
    private String description;
    private String funFact;
    private boolean aloneActivity;
    private int XP;

    public Tegevus(String title, String description, String funFact, boolean aloneActivity, int XP) {
        this.title = title;
        this.description = description;
        this.funFact = funFact;
        this.aloneActivity = aloneActivity;
        this.XP = XP;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFunFact() {
        return funFact;
    }

    public void setFunFact(String funFact) {
        this.funFact = funFact;
    }

    public boolean isAloneActivity() {
        return aloneActivity;
    }

    public void setAloneActivity(boolean aloneActivity) {
        this.aloneActivity = aloneActivity;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }
}
