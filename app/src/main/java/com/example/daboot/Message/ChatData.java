package com.example.daboot.Message;


public class ChatData {

    private String usremail;
    private String msg;
    private String nick;
    private String time;

    public ChatData(){}

    public ChatData(String usremail, String msg, String nick, String time){
        this.usremail = usremail;
        this.msg = msg;
        this.nick = nick;
        this.time = time;

    }

    public String getUsremail() {
        return usremail;
    }

    public void setUsremail(String usremail) {
        this.usremail = usremail;
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
