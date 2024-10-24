package com.movieflix.movie_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movie {

    @Getter
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

    @Setter
    @Getter
    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Getter
    @Column(nullable = false)
    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "Please provide movie's poster!")
    private String poster;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Wishlist> wishlistedByUsers;

    public Movie(Integer movieId, String title, String director, String studio, Set<String> movieCast, String poster, Integer releaseYear) {
        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.studio = studio;
        this.movieCast = movieCast;
        this.poster = poster;
        this.releaseYear = releaseYear;
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

    public @NotBlank(message = "Please provide movie's poster!") String getPoster() {
        return poster;
    }

    public void setPoster(@NotBlank(message = "Please provide movie's poster!") String poster) {
        this.poster = poster;
    }
}
