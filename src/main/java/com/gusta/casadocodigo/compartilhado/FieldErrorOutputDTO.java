package com.gusta.casadocodigo.compartilhado;

public class FieldErrorOutputDTO {

    private String field;
    private String message;

    public FieldErrorOutputDTO() {
    }

    public FieldErrorOutputDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
