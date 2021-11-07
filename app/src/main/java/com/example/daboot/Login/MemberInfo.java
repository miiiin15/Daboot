package com.example.daboot.Login;

public class MemberInfo {

    private String name;
    private String sex;
    private String email;
    private String area;
    private String field;
    private String qual;
    private String contents;

    public MemberInfo(String name, String sex, String email, String area, String field, String qual, String contents) {
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.area = area;
        this.field = field;
        this.qual = qual;
        this.contents = contents;
    }

    public MemberInfo(String name, String sex, String area, String field) {
        this.name = name;
        this.sex = sex;
        this.area = area;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
