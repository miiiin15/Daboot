package com.example.daboot.Message;


public class ChatData {

    private String idToken;
    private String msg;
    private String nick;
    private String time;

    public ChatData(){}

    public ChatData(String idToken, String msg, String nick, String time){
        this.idToken = idToken;
        this.msg = msg;
        this.nick = nick;
        this.time = time;

    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
