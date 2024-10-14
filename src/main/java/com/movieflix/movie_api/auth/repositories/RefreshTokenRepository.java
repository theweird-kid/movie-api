package com.movieflix.movie_api.auth.repositories;

import com.movieflix.movie_api.auth.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
}
