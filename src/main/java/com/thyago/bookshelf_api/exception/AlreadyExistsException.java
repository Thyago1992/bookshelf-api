package com.thyago.bookshelf_api.exception;

public class AlreadyExistsException extends RuntimeException {

    public static final String MSG_LIVRO_JA_EXISTE = "Livro já existe";
    public static final String MSG_CATEGORIA_JA_EXISTE = "Categoria já existe";

    public AlreadyExistsException(String message) {
        super(message);
    }
}