package com.example.daboot.Board;

public class ComentItem {

    private String uid;
    private String writer;
    private int number;
    private String coment;
    private String time;

    public ComentItem(String uid, String writer, int number, String coment, String time) {
        this.uid = uid;
        this.writer = writer;
        this.number = number;
        this.coment = coment;
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
}