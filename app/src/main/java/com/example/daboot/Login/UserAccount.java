package com.example.daboot.Login;

public class UserAccount {
    private String idToken; //Firebase UID 고유 정보
    private String userID,userPWD,userAREA,userQUALIFICATION,userFIELD;

    public UserAccount() { }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPWD() {
        return userPWD;
    }

    public void setUserPWD(String userPWD) {
        this.userPWD = userPWD;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getUserAREA() { return userAREA; }

    public void setUserAREA(String userAREA) { this.userAREA = userAREA; }

    public String getUserQUALIFICATION() { return userQUALIFICATION; }

    public void setUserQUALIFICATION(String userQUALIFICATION) { this.userQUALIFICATION = userQUALIFICATION; }

    public String getUserFIELD() { return userFIELD; }

    public void setUserFIELD(String userFIELD) { this.userFIELD = userFIELD; }
}
