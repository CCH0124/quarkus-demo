package com.cch;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import com.cch.entity.Movie;
import com.cch.service.MovieService;
import com.oracle.svm.core.annotate.Inject;

@Path("/movie")
public class MovieResource {

    @Inject
    MovieService movieService;

    @Inject
    Logger logger;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Movie insert(Movie movie) {

        logger.info("New Movie inserted " + movie.getName());
       
        return movieService.insertMovie(movie);
    }


    
}
