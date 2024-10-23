package com.movieflix.movie_api.controllers;

import com.movieflix.movie_api.auth.entities.User;
import com.movieflix.movie_api.auth.repositories.UserRepository;
import com.movieflix.movie_api.auth.services.JwtService;
import com.movieflix.movie_api.entities.Movie;
import com.movieflix.movie_api.entities.Wishlist;
import com.movieflix.movie_api.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    private final JwtService jwtService;

    public WishlistController(WishlistService wishlistService,
                              JwtService jwtService)
    {
        this.wishlistService = wishlistService;
        this.jwtService = jwtService;
    }

    @PostMapping("/add")
    public ResponseEntity<Wishlist> addMovieToWishlist(
            @RequestHeader("Authorization") String token,
            @RequestParam Integer movieId
    )
    {
        // Extract userId from token
        String jwtToken = token.substring(7);
        String email = jwtService.extractUsername(jwtToken);
        System.out.println(email);

        Wishlist wishlist = wishlistService.addMovieToWishlist(email, movieId);
        return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Movie>> getWishlistByUser(
            @RequestHeader("Authorization") String token
    )
    {
        // Extract userId from token
        String jwtToken = token.substring(7);
        String email = jwtService.extractUsername(jwtToken);


        List<Movie> movies = wishlistService.getWishlistByUserEmail(email);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}
