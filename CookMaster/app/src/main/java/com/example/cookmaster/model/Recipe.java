package com.example.cookmaster.model;

public class Recipe {
    private int ID;
    private String name;
    private String Description;
    private int Duration;
    private String ComplexityLevel;
    private String Video;

    public Recipe(int ID, String name, String description, int duration, String complexityLevel, String video) {
        this.ID = ID;
        this.name = name;
        this.Description = description;
        this.Duration = duration;
        this.ComplexityLevel = complexityLevel;
        this.Video = video;
    }

    // Getter and Setter methods
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        this.Duration = duration;
    }

    public String getComplexityLevel() {
        return ComplexityLevel;
    }

    public void setComplexityLevel(String complexityLevel) {
        this.ComplexityLevel = complexityLevel;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        this.Video = video;
    }
}

