package com.galvanize.gmoviedb.service;

import com.galvanize.gmoviedb.domain.Movie;
import org.springframework.data.repository.CrudRepository;

public interface GBMovieRepo extends CrudRepository<Movie, Long> {


}
