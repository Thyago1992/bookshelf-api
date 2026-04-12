package com.thyago.bookshelf_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para resposta de erros em requisições.")
public class ErroResponseDTO {

    @Schema(description = "O código de status HTTP do erro.", example = "404")
    private int status;

    @Schema(description = "A mensagem de erro detalhada.", example = "Livro não encontrado para o ID fornecido.")
    private String mensagem;

    @Schema(description = "O timestamp indicando quando o erro ocorreu.", example = "2024-06-01T12:34:56")
    private LocalDateTime timestamp;

    public ErroResponseDTO(int status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = LocalDateTime.now();
    }

}
