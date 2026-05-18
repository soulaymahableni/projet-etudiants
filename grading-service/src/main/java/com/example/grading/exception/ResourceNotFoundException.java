package com.example.grading.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Object id) {
        super(resource + " avec l'identifiant " + id + " est introuvable");
    }
}
