package com.movieflix.movie_api.service.impl;

import com.movieflix.movie_api.dto.MovieDto;
import com.movieflix.movie_api.entities.Movie;
import com.movieflix.movie_api.repositories.MovieRepository;
import com.movieflix.movie_api.service.FileService;
import com.movieflix.movie_api.service.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    //@Value("${BASE_URL}")
    private String baseUrl = "http://localhost:8080";

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {

        // upload the file
        String uploadedFileName = fileService.uploadFile(path, file);
        // set the value of field 'poster' as filename
        movieDto.setPoster(uploadedFileName);
        // map dto to Movie Object
        Movie movie = new Movie(
                movieDto.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getPoster(),
                movieDto.getReleaseYear()
        );
        // save the Movie object
        Movie savedMovie = movieRepository.save(movie);
        // generate the posterUrl
        String posterUrl = baseUrl + "/file/" + uploadedFileName;
        // map Movie object to DTO object and return it
        MovieDto response = new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );

        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        // check the data in DB and if exists, fetch the data of given ID
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie Not Found!"));
        // generate posterUrl
        String posterUrl = baseUrl + "/file/" + movie.getPoster();
        // map to MovieDto object and return it
        MovieDto response = new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        // fetch all data from DB
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtos = new ArrayList<>();
        // Iterate through the list and Generate posterUrl for each movie obj, and map to MovieDto object
        for(Movie movie: movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto res = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(res);
        }
        return movieDtos;
    }
}
