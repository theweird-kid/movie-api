package com.movieflix.movie_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide movie title!")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Please provide movie director!")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "Please provide movie studio!")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false)
    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "Please provide movie's poster!")
    private String poster;

    public Movie(Integer movieId, String title, String director, String studio, Set<String> movieCast, String poster, Integer releaseYear) {
        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.studio = studio;
        this.movieCast = movieCast;
        this.poster = poster;
        this.releaseYear = releaseYear;
    }


    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public @NotBlank(message = "Please provide movie title!") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Please provide movie title!") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Please provide movie director!") String getDirector() {
        return director;
    }

    public void setDirector(@NotBlank(message = "Please provide movie director!") String director) {
        this.director = director;
    }

    public Set<String> getMovieCast() {
        return movieCast;
    }

    public void setMovieCast(Set<String> movieCast) {
        this.movieCast = movieCast;
    }

    public @NotBlank(message = "Please provide movie studio!") String getStudio() {
        return studio;
    }

    public void setStudio(@NotBlank(message = "Please provide movie studio!") String studio) {
        this.studio = studio;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public @NotBlank(message = "Please provide movie's poster!") String getPoster() {
        return poster;
    }

    public void setPoster(@NotBlank(message = "Please provide movie's poster!") String poster) {
        this.poster = poster;
    }
}
