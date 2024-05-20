package br.edu.ifsp.pw3.api.util;

import br.edu.ifsp.pw3.api.medico.DadosCadastroMedico;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //metodo para trazer uma mensagem de rro mais amigavem no body com apenas dois campos
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        var lista= erros.stream().map(DadosErroValidacao::new).toList();

        return ResponseEntity.badRequest().body(lista);
    }

    private record DadosErroValidacao(String campo, String msgErro){
        public DadosErroValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
