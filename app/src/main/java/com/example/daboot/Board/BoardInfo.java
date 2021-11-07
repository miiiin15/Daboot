package com.example.daboot.Board;

public class BoardInfo {

    private String category;
    private String anonymous;
    private String title;
    private String writer;
    private String contents;
    private String time;

    public BoardInfo(String category, String anonymous, String title, String writer, String contents, String time) {
        this.category = category;
        this.anonymous = anonymous;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
