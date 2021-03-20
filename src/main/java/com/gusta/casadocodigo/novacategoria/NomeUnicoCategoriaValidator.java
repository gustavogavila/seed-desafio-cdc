package com.gusta.casadocodigo.novacategoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class NomeUnicoCategoriaValidator implements Validator {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public NomeUnicoCategoriaValidator(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return NovaCategoriaRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        NovaCategoriaRequest request = (NovaCategoriaRequest) o;
        Optional<Categoria> possivelCategoria = categoriaRepository.findByNome(request.getNome());
        if (possivelCategoria.isPresent()) {
            errors.rejectValue("nome", "UniqueValue.categoria.nome", "Esta categoria já existe");
        }
    }
}
