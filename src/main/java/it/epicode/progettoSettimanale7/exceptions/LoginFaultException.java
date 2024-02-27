package it.epicode.progettoSettimanale7.exceptions;

public class LoginFaultException extends RuntimeException{
    public LoginFaultException(String msg){
        super(msg);
    }
}
