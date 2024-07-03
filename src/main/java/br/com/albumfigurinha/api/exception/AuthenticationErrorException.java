package br.com.albumfigurinha.api.exception;

public class AuthenticationErrorException extends RuntimeException{
    public AuthenticationErrorException(String message){
        super(message);
    }
}
