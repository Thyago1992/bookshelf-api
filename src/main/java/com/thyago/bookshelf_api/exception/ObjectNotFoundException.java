package com.thyago.bookshelf_api.exception;

public class ObjectNotFoundException extends RuntimeException {

    public static final String MSG_LIVRO_NAO_ENCONTRADO = "Livro não encontrado";
    public static final String MSG_CATEGORIA_NAO_ENCONTRADA = "Categoria não encontrada";

    public ObjectNotFoundException(String message) {
        super(message);
    }
}