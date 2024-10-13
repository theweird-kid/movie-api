package com.movieflix.movie_api.exceptions;

public class EmptyFileException extends Throwable{

    public EmptyFileException(String message) {
        super(message);
    }
}
