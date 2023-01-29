package com.cch.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.cch.entity.Movie;

@Mapper
public interface MovieMapper {
    String MOVIE_TABLE = "movie";

    @Insert("INSERT INTO " + MOVIE_TABLE + " (name, director, genre) VALUES (#{name}, #{director}, #{genre}) ")
    void persist(Movie movie);

}
