package br.edu.ifsp.pw3.api.medico;

import br.edu.ifsp.pw3.api.enums.Especialidade;

public record DadosListagemMedico(Long id, String nome, String email,
                                  String crm, Especialidade especialidade

) {
    public DadosListagemMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(),
                medico.getCrm(), medico.getEspecialidade() );
    }
}
