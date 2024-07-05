package br.com.albumfigurinha.api.exception;

public class StickerNotFoundException extends RuntimeException{
    public StickerNotFoundException(String message){
        super(message);
    }
}
