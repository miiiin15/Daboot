package com.example.daboot.Message;

public class ChatRoomData {
    private String wrterIdToken;
    private String userIdToken;

    public ChatRoomData(){}

    public ChatRoomData(String writerIdToken, String userIdToken){
        writerIdToken = this.wrterIdToken;
        userIdToken = this.userIdToken;
    }

    public String getWrterIdToken() {
        return wrterIdToken;
    }

    public void setWrterIdToken(String wrterIdToken) {
        this.wrterIdToken = wrterIdToken;
    }

    public String getUserIdToken() {
        return userIdToken;
    }

    public void setUserIdToken(String userIdToken) {
        this.userIdToken = userIdToken;
    }
}
