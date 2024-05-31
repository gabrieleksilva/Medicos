package br.edu.ifsp.pw3.api.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
        @NotBlank
        String login,
        @NotBlank
        String senha
) {
}
