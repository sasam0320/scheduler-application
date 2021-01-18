package com.scheduler.springboot.exception;

public class TaskNotFoundException extends Exception {

    private String message;

    public TaskNotFoundException(String message){

        this.message = message;
    }

    @Override
    public String getMessage() {

        return this.message ;
    }
}
