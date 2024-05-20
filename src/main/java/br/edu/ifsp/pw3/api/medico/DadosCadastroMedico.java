package br.edu.ifsp.pw3.api.medico;

import br.edu.ifsp.pw3.api.endereco.DadosEndereco;
import br.edu.ifsp.pw3.api.enums.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

//DTO
public record DadosCadastroMedico(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull
        Especialidade especialidade,
        @NotNull
        String telefone,
        @NotNull
        @Valid
        DadosEndereco endereco) {

}
