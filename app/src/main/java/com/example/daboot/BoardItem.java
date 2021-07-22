package com.example.daboot;

public class BoardItem {

    String category;
    String title;
    String coment;
    String time;

    public BoardItem(String category, String title, String coment, String time) {
        this.category = category;
        this.title = title;
        this.coment = coment;
        this.time = time;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}