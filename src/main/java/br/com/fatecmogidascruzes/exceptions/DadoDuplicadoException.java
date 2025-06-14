package br.com.fatecmogidascruzes.exceptions;

public class DadoDuplicadoException extends RuntimeException {

    public DadoDuplicadoException(String message) {
        super(message);
    }

    public DadoDuplicadoException(String message, Throwable cause) {
        super(message, cause);
    }
}