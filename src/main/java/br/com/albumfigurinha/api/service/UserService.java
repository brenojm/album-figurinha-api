package br.com.albumfigurinha.api.service;

import br.com.albumfigurinha.api.dto.UserCreationDTO;
import br.com.albumfigurinha.api.dto.UserDTO;
import br.com.albumfigurinha.api.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    UserDTO createUser(UserCreationDTO userDTO);

    UserDTO getUserById(String userId);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UserCreationDTO userDTO, String userId);

    void deleteUser(String userId);
}