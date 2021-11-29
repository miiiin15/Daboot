package com.example.daboot.Message;

public class RoomPerUserData {

    private String userEmail;
    private String roomId;

    RoomPerUserData(){}

    public RoomPerUserData(String userEmail, String roomId){
        userEmail = this.userEmail;
        roomId = this.roomId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
