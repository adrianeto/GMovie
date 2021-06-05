package com.galvanize.gmoviedb.controller;

import com.galvanize.gmoviedb.domain.Movie;
import com.galvanize.gmoviedb.service.GBMovieRepo;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserController {

    private final GBMovieRepo repo;

    public UserController(GBMovieRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/Movies")
    public Iterable<Movie> listMovies(){
        return this.repo.findAll();
    }

    @PostMapping("/Movie")
    public  Movie create(@RequestBody Movie movie) {
        this.repo.save(movie);

         return this.repo.findByTitle(movie.getTitle());
    }

    @GetMapping("/Movie/{title}")
    public Integer ratingAverageByTitle( @PathVariable String title ){
       Movie movie = this.repo.findByTitle(title);
       return movie.getRating();
    }

    public Integer ratingAvg(Movie movie, Integer rating){

        if ( movie.getRating() != null) {
            rating = ( movie.getRating() + rating) / 2;
        }else {
            rating = 0;
        }
        return rating;
    }
    @PostMapping("/Movies")
    public void storeMovies(@RequestBody ArrayList<Movie> movie) {

        for (Movie item: movie)
        {
           this.repo.save(item);
        }
    }

    @GetMapping("/Movie")
    public ResponseEntity<Object> getMovieByTitle(@RequestParam String title){
        Movie myMovie = this.repo.findByTitle(title);
        if (myMovie == null) {
            //return new String("{message: " +
            HttpHeaders header = new HttpHeaders();
            header.add("desc", "a Header description");
            String msg = "Not existent movie";
            return new ResponseEntity(msg, header, HttpStatus.OK );
        }
        else{
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(myMovie, header, HttpStatus.OK );

        }

    }
    @PatchMapping("/Movie")
    public Movie updateMovie(@RequestParam String title, @RequestParam Integer rating, @RequestParam String review){
        Movie movie = this.repo.findByTitle(title);
        movie.setRating(ratingAvg(movie,rating));
        movie.setReview(review);

        this.repo.save(movie);

        return movie;

    }

    @PatchMapping("/Movie")
    public Movie updateMovieRating(@RequestParam String title, @RequestParam Integer rating){
        Movie movie = this.repo.findByTitle(title);
        movie.setRating(ratingAvg(movie,rating));

        this.repo.save(movie);

        return movie;

    }




}
