package com.defatov.todolist_spring_usage.exceptions;

public class NullEntityReferenceException extends RuntimeException{

    public NullEntityReferenceException() {
    }

    public NullEntityReferenceException(String message) {
        super(message);
    }
}
