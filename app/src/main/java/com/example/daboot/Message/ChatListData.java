package com.example.daboot.Message;

public class ChatListData {

    private String profile;
    private String nick;
    private String chatContent;
    private String time;

    public ChatListData(){}

    /*public ChatListData(String profile, String nick, String chatContent, String time){
        this.profile = profile;
        this.nick = nick;
        this.chatContent = chatContent;
        this.time = time;
    }*/

    public String getProfile()
    {
        return this.profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getNick()
    {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getChatContent()
    {
        return this.chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public String getTime()
    {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
