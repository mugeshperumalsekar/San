package com.ponsun.san.infrastructure.utils;

import java.util.UUID;

public class Response {

    private Object id;
    private String message;

    public Response() {}

    public Response(Object id) {
        this.id = id;
    }

    public Response(Object id, String message) {
        this.id = id;
        this.message = message;
    }

    public static Response of(Integer id) {
        return new Response(id);
    }

    public static Response of(Long id) {
        return new Response(id);
    }

    public static Response of(UUID id) {
        return new Response(id);
    }

    public static Response of(Integer id, String message) {
        return new Response(id, message);
    }

    public static Response of(Long id, String message) {
        return new Response(id, message);
    }

    public static Response of(UUID id, String message) {
        return new Response(id, message);
    }

    public static Response empty() {
        return new Response(0L);
    }

    public Object getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{id: " + id + ", message: \"" + message + "\"}";
    }
}
