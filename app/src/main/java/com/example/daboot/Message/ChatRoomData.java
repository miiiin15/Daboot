package com.example.daboot.Message;

public class ChatRoomData {
    private String roomIdToken;
    private String writerEmail;
    private String userEmail;

    public ChatRoomData(){}

    public ChatRoomData(String roomIdToken, String writerEmail, String userEmail){
        roomIdToken = this.roomIdToken;
        writerEmail = this.writerEmail;
        userEmail = this.userEmail;
    }

    public String getRoomIdToken() {
        return roomIdToken;
    }

    public void setRoomIdToken(String roomIdToken) {
        this.roomIdToken = roomIdToken;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
