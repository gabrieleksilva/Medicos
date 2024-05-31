package br.edu.ifsp.pw3.api.util.security;

import br.edu.ifsp.pw3.api.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private PW3TokenService pw3tokenservice;
    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Recuperando o cabeçalho Authorization (o token JWT):
        var authorizationHeader = request.getHeader("Authorization");
        // Lembrando: /login não envia cabeçalho authorization!
        // Se o JWT foi enviado...
        if (authorizationHeader != null) {
            // Tira o 'Bearer':
            authorizationHeader = authorizationHeader.replace("Bearer ", "");
            // Chamar o método que valida o token, e recupera o login:
            var subject = pw3tokenservice.getSubject(authorizationHeader);
            // Carrega o usuário com esse login do BD:
            var usuario = repository.findByLogin(subject);
            // Cria um objeto Authentication que representa a identidade de um usuário autenticado.
            // Ele contém as informações do usuário, como o nome de usuário, a senha e as permissões.
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                    usuario.getAuthorities());
            // Finalmente, manda o Spring AUTENTICAR esse usuário:
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // Para executar o próximo filtro, ou seguir o processamento:
        filterChain.doFilter(request, response);
    }
}
