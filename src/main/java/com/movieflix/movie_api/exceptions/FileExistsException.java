package com.movieflix.movie_api.exceptions;

public class FileExistsException extends RuntimeException{

    public FileExistsException(String message) {
        super(message);
    }
}
