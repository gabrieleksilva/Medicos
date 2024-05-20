package br.edu.ifsp.pw3.api.controller;

import br.edu.ifsp.pw3.api.medico.*;
import br.edu.ifsp.pw3.api.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados,
                                    UriComponentsBuilder uriBuilder){
        // ANTES: repository.save( new Medico(dados) );
        // AGORA:
        // Criando o objeto Medico, com os dados recebidos na requisição:
        var medico = new Medico(dados);
        // Salvando no banco:
        repository.save( medico );
        // Gerar automaticamente a URL para o novo recurso criado:
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        // Vamos aqui usar o DTO que criamos para o método atualizar:
        return ResponseEntity.created(uri).body( new DadosDetalhamentoMedico(medico) );

    }

    @GetMapping
    public ResponseEntity listar(){
        return ResponseEntity.ok(repository.findAll());
    }
    /*@GetMapping("algunsDados")
    public List<DadosListagemMedico> listarAlgunsDados(){
        return repository.findAll().stream().map(DadosListagemMedico::new).toList();
    }*/

    @GetMapping("algunsDados")
    public ResponseEntity listarAlgunsDados(
            @PageableDefault(
                  //  size=2,
                    size=10,
                    //page=0,
                    sort={"nome","crm"}
                   // , direction = Sort.Direction.DESC
            ) Pageable paginacao )
    {   // traz todos os registros ativos e inativos
        //return repository.findAll(paginacao).map(DadosListagemMedico::new);
        var pagina = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> getMedicoById(@PathVariable Long id) {
        Optional<Medico> medicoOptional = repository.findById(id);
        if (medicoOptional.isPresent()) {
            Medico medico = medicoOptional.get();
            return ResponseEntity.ok(medico);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        Medico medico = repository.getReferenceById( dados.id() );
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    // Delete fisico no banco de dados
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void excluir(@PathVariable Long id) {
//        repository.deleteById(id);
//    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);
        // Chama método na classe Medico que coloca false no atributo 'ativo':
        medico.excluir();
        // Lembrando, não precisamos regravar o objeto no BD.
        // A JPA automaticamente atualiza o objeto no BD.
        return ResponseEntity.noContent().build();
    }
}
