package com.example.examenjava.models;

public class User {
    private  String nick;
    private  int ducks;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getDucks() {
        return ducks;
    }

    public void setDucks(int ducks) {
        this.ducks = ducks;
    }

    public User(String nick, int ducks) {
        this.nick = nick;
        this.ducks = ducks;
    }

    public User() {
    }
}
