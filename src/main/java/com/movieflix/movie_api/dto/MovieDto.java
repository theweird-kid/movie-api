package com.movieflix.movie_api.dto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class MovieDto {
    private Integer movieId;

    @NotBlank(message = "Please provide movie title!")
    private String title;

    @NotBlank(message = "Please provide movie director!")
    private String director;

    @NotBlank(message = "Please provide movie studio!")
    private String studio;

    private Set<String> movieCast;

    private Integer releaseYear;

    @NotBlank(message = "Please provide movie's poster!")
    private String poster;

    @NotBlank(message = "Please provide poster's url")
    private String posterUrl;


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

    public @NotBlank(message = "Please provide movie studio!") String getStudio() {
        return studio;
    }

    public void setStudio(@NotBlank(message = "Please provide movie studio!") String studio) {
        this.studio = studio;
    }

    public Set<String> getMovieCast() {
        return movieCast;
    }

    public void setMovieCast(Set<String> movieCast) {
        this.movieCast = movieCast;
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

    public @NotBlank(message = "Please provide poster's url") String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(@NotBlank(message = "Please provide poster's url") String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
