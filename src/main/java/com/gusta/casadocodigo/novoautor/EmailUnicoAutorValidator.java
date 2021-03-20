package com.gusta.casadocodigo.novoautor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class EmailUnicoAutorValidator implements Validator {
    private final AutorRepository autorRepository;

    @Autowired
    public EmailUnicoAutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return NovoAutorRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        NovoAutorRequest request = (NovoAutorRequest) o;
        Optional<Autor> possivelAutor = autorRepository.findByEmail(request.getEmail());
        if (possivelAutor.isPresent()) {
            errors.rejectValue("email", "UniqueValue.novoAutorRequest.email", "E-mail " + request.getEmail() +
                    " já existe");
        }
    }
}
