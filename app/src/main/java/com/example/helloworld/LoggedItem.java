package com.example.helloworld;

import java.util.List;
public class LoggedItem {
    String number, nameLogged, date, time, audioId, photoId, fieldNote, location;
    boolean seen, heard, captured;

    public LoggedItem(String number, String nameLogged, String date, String time, String audioId, String photoId, String filedNote, String location, boolean seen, boolean heard, boolean captured){
        this.number = number;
        this.nameLogged = nameLogged;
        this.date = date;
        this.time = time;
        this.audioId = audioId;
        this.photoId = photoId;
        this.fieldNote = filedNote;
        this.location = location;
        this.seen = seen;
        this.heard = heard;
        this.captured = captured;
    }

    public String getNumber() {
        return number;
    }

    public String getNameLogged() {
        return nameLogged;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAudioId() {
        return audioId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getFieldNote() {
        return fieldNote;
    }

    public String getLocation() {
        return location;
    }

    public boolean isSeen() {
        return seen;
    }

    public boolean isHeard() {
        return heard;
    }

    public boolean isCaptured() {
        return captured;
    }
}
