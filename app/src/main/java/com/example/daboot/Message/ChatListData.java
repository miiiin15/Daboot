package com.example.daboot.Message;

public class ChatListData {

    private String nick;
    private String chatContent;
    private String time;

    public ChatListData(){}

    public ChatListData(String nick, String chatContent, String time){
        this.nick = nick;
        this.chatContent = chatContent;
        this.time = time;
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
