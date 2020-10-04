package com.example.puppypals_foryourpooch;

public class Admin {
    String id;
    String uname;
    String pswd;

    public Admin() {
    }

    public Admin(String id, String uname, String pswd) {
        this.id = id;
        this.uname = uname;
        this.pswd = pswd;
    }

    public Admin(String uname, String pswd) {
        this.uname = uname;
        this.pswd = pswd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id='" + id + '\'' +
                ", uname='" + uname + '\'' +
                ", pswd='" + pswd + '\'' +
                '}';
    }
}
