package com.example.daboot.Board;

public class BoardInfo {

    private String category;
    private String anonymous;
    private String title;
    private String writer;
    private String contents;
    private int coments;
    private String time;
    private String imgpath;

    public BoardInfo(String category, String anonymous, String title, String writer, String contents,int coments, String time,String imgpath) {
        this.category = category;
        this.anonymous = anonymous;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.coments = coments;
        this.time = time;
        this.imgpath = imgpath;
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

    public int getComents() {
        return coments;
    }

    public void setComents(int coments) {
        this.coments = coments;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }
}
