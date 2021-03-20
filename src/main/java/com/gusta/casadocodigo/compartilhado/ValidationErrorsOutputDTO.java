package com.gusta.casadocodigo.compartilhado;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationErrorsOutputDTO {

    private final List<String> globalErrorMessages = new ArrayList<>();
    private final List<FieldErrorOutputDTO> fieldErros = new ArrayList<>();
    private Integer totalErros;
    private final Instant timeStamp = Instant.now();

    public void addError(String message) {
        globalErrorMessages.add(message);
    }

    public void addFieldError(String field, String message) {
        FieldErrorOutputDTO fieldError = new FieldErrorOutputDTO(field, message);
        fieldErros.add(fieldError);
    }

    public List<String> getGlobalErrorMessages() {
        return globalErrorMessages;
    }

    public List<FieldErrorOutputDTO> getFieldErros() {
        return fieldErros;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public Integer getTotalErros() {
        return globalErrorMessages.size() + fieldErros.size();
    }
}
