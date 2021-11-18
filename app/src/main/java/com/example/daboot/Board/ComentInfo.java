package com.example.daboot.Board;

public class ComentInfo {

    private String writer;
    private String coment;
    private String time;

    public ComentInfo(String writer, String coment, String time) {
        this.writer = writer;
        this.coment = coment;
        this.time = time;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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
