package br.com.albumfigurinha.api.service.impl;

import br.com.albumfigurinha.api.exception.UserNotFoundException;
import br.com.albumfigurinha.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with the given Username: { " + username + " }"));
        } catch (UserNotFoundException ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }
}

