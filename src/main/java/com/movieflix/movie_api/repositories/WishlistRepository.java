package com.movieflix.movie_api.repositories;

import com.movieflix.movie_api.entities.Movie;
import com.movieflix.movie_api.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    @Query("SELECT w.movie FROM Wishlist w WHERE w.user.id = :userId")
    List<Movie> findMoviesByUserId(@Param("userId") Integer userId);
}
