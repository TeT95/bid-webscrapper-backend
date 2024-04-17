package com.tet.webscrapper.exceptions;

public class BidNotFoundException extends RuntimeException {
    public BidNotFoundException(String mensagem) {
        super(mensagem);
    }
}
