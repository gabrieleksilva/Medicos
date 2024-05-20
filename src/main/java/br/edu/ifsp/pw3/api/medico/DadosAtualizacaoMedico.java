package br.edu.ifsp.pw3.api.medico;
import br.edu.ifsp.pw3.api.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) { }
