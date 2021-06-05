package com.galvanize.gmoviedb.controller;

import com.galvanize.gmoviedb.domain.Movie;
import com.galvanize.gmoviedb.service.GBMovieRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class User {

    private final GBMovieRepo repo;

    public User(GBMovieRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/User")
    public Iterable<Movie> listUser(){
        return this.repo.findAll();
    }
}
