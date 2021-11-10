package com.example.library.models;

public class Book {
    private String _id ;
    private String _author ;
    private String _title ;
    private String _cover ;

    public Book(String author, String title, String cover) {
        this._author = author;
        this._title = title;
        this._cover = cover;
    }

    public Book(String id, String author, String title, String cover) {
        this._id = id;
        this._author = author;
        this._title = title;
        this._cover = cover;
    }

    public String getId() {return _id;}
    public void setId(String id) {this._id = id;}

    public String getAuthor() {return _author;}
    public void setAuthor(String author) {this._author = author;}

    public String getTitle() {return _title;}
    public void setTitle(String title) {this._title = title;}

    public String getCover() {return _cover;}
    public void setCover(String cover) {this._cover = cover;}
}
