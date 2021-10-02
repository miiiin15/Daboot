package com.example.daboot.Message;


import android.util.Log;

public class ChatData {
    private String msg;
    private String nick;
    private String time;


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
