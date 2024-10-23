package com.movieflix.movie_api.service.impl;

import com.movieflix.movie_api.auth.entities.User;
import com.movieflix.movie_api.auth.repositories.UserRepository;
import com.movieflix.movie_api.entities.Movie;
import com.movieflix.movie_api.entities.Wishlist;
import com.movieflix.movie_api.exceptions.MovieNotFoundException;
import com.movieflix.movie_api.repositories.MovieRepository;
import com.movieflix.movie_api.repositories.WishlistRepository;
import com.movieflix.movie_api.service.WishlistService;
import org.aspectj.bridge.IMessage;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    WishlistServiceImpl(WishlistRepository wishlistRepository,
                        UserRepository userRepository,
                        MovieRepository movieRepository)
    {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public Wishlist addMovieToWishlist(String username, Integer movieId) {

        // fetch the User by ID
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with email = " + username));

        // find movie with the give movieId
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("movie not found with id = " + movieId));

        // add movie to wishlist
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setMovie(movie);

        return wishlistRepository.save(wishlist);
    }

    @Override
    public List<Movie> getWishlistByUserEmail(String email) {
        // fetch the User by ID
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with id = " + email));

        return wishlistRepository.findMoviesByUserId(user.getUserId());
    }
}
