package br.com.albumfigurinha.api.config.auth;

import br.com.albumfigurinha.api.dto.ErrorDTO;
import br.com.albumfigurinha.api.exception.UserNotFoundException;
import br.com.albumfigurinha.api.repository.UserRepository;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenProvider tokenService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var token = this.recoverToken(request);
            if (token != null) {
                var login = tokenService.validateToken(token);
                var optionalUser = userRepository.findByUsername(login);
                if (optionalUser.isEmpty()) {
                    throw new UserNotFoundException("User not found with the given Username: { " + login + " }");
                }
                UserDetails user = optionalUser.get();

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (UserNotFoundException | JWTVerificationException ex) {
            handleException(response, ex, HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException ex) {
            handleException(response, ex, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            handleException(response, ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }

    private void handleException(HttpServletResponse response, Exception exception, HttpStatus status) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        ErrorDTO errorDTO = new ErrorDTO(exception.getMessage());
        new ObjectMapper().writeValue(response.getOutputStream(), errorDTO);
    }
}
