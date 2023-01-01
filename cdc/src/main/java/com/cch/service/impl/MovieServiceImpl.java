package com.cch.service.impl;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.cch.entity.Movie;
import com.cch.mapper.MovieMapper;
import com.cch.service.MovieService;

import io.debezium.outbox.quarkus.ExportedEvent;



public class MovieServiceImpl implements MovieService{

    @Inject
    MovieMapper movieMapper;

    @Override
    @Transactional
    public Movie insertMovie(Movie movie) {
        movieMapper.persist(movie); 
        return movie;
    }    
}
