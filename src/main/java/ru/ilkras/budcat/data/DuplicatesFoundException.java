package ru.ilkras.budcat.data;

public class DuplicatesFoundException extends Exception {
    public DuplicatesFoundException(String errorMessage) {
        super(errorMessage);
    }
}
