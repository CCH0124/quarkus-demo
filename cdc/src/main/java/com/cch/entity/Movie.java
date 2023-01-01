package com.cch.entity;

public class Movie {
    private  String id;
    private  String name;
    private  String director;
    private  String genre;

    
    
    public Movie() {
    }

    public Movie(String id, String name, String director, String genre) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Movie [id=" + id + ", name=" + name + ", director=" + director + ", genre=" + genre + "]";
    }
    
    
}
