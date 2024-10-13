package com.movieflix.movie_api.service.impl;

import com.movieflix.movie_api.dto.MovieDto;
import com.movieflix.movie_api.entities.Movie;
import com.movieflix.movie_api.exceptions.FileExistsException;
import com.movieflix.movie_api.exceptions.MovieNotFoundException;
import com.movieflix.movie_api.repositories.MovieRepository;
import com.movieflix.movie_api.service.FileService;
import com.movieflix.movie_api.service.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        if(Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileExistsException("File already exists! Please Enter another file name");
        }
        String uploadedFileName = fileService.uploadFile(path, file);
        // set the value of field 'poster' as filename
        movieDto.setPoster(uploadedFileName);
        // map dto to Movie Object
        Movie movie = new Movie(
               null,
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
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie Not Found with ID = " + movieId));
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

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        // Check if movie object exists with given movieId
        Movie mv = movieRepository.findById(movieId).
                orElseThrow(() -> new MovieNotFoundException("Movie Not Found with ID = " + movieId));

        // if file is null, then do nothing
        // else delete existing file associated with the record, and upload the new file
        String fileName = mv.getPoster();
        if(file != null) {
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path, file);
        }

        // Set movieDto's poster value
        movieDto.setPoster(fileName);

        // Map it to movie object
        Movie movie = new Movie(
                mv.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getPoster(),
                movieDto.getReleaseYear()
        );

        // Save the movie Object
        Movie updatedMovie = movieRepository.save(movie);

        // generate posterUrl for it
        String posterUrl = baseUrl + "/file/" + fileName;

        // map to movieDto and return it
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
    public String deleteMovie(Integer movieId) throws IOException {
        // Check if movie object exists with given movieId
        Movie mv = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie Not Found with ID = " + movieId));
        Integer id = mv.getMovieId();

        // delete the file associated with this object
        Files.deleteIfExists(Paths.get(path + File.separator + mv.getPoster()));

        // delete the movie object'
        movieRepository.delete(mv);

        return "Movie deleted with id = " + id;
    }
}
