package br.edu.ifsp.pw3.api.medico;

import br.edu.ifsp.pw3.api.endereco.Endereco;
import br.edu.ifsp.pw3.api.enums.Especialidade;

public record DadosDetalhamentoMedico(Long id, String nome, String email,
                                      String crm, String telefone,
                                      Especialidade especialidade, Endereco endereco) {
    public DadosDetalhamentoMedico (Medico medico) {
        this( medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(),
                medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco() );
    }
}
