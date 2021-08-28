package com.example.daboot;

import java.io.Serializable;

public class ChatData {
    private String msg;
    private String nick;

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
}
