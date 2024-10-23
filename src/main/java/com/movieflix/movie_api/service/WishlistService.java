package com.movieflix.movie_api.service;

import com.movieflix.movie_api.entities.Movie;
import com.movieflix.movie_api.entities.Wishlist;

import java.util.List;

public interface WishlistService {

    Wishlist addMovieToWishlist(String username, Integer movieId);

    List<Movie> getWishlistByUserEmail(String email);
}
