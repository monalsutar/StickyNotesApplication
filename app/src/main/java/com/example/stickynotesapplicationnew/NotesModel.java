package com.example.stickynotesapplicationnew;

public class NotesModel {

    private String id;
    private String title;
    private String contents;
    private String uid;

    public NotesModel() {

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public NotesModel(String id, String title, String contents, String uid) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}
