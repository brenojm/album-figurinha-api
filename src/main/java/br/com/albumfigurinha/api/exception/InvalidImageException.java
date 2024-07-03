package br.com.albumfigurinha.api.exception;

public class InvalidImageException extends RuntimeException{
    public InvalidImageException(String message){
        super(message);
    }
}
