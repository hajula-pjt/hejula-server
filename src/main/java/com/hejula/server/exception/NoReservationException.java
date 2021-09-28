package com.hejula.server.exception;


public class NoReservationException extends RuntimeException {

    private static final long serialVersionUID = 4574994639007519716L;

    public NoReservationException(){ super(); }

    public NoReservationException(String msg){
        super(msg);
    }
}
