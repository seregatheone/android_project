package com.example.project_x;

class Olimp {
    private String title,description,link;
    private String rating;

    public Olimp(String name,String description, String rating,String link) {
        this.title = name;
        this.description = description;
        this.rating = rating;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}