package br.com.albumfigurinha.api.mapper;

import br.com.albumfigurinha.api.dto.UserCreationDTO;
import br.com.albumfigurinha.api.dto.UserDTO;
import br.com.albumfigurinha.api.entity.User;
import br.com.albumfigurinha.api.entity.enums.Roles;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserDTO toDto(User user) {
        return new UserDTO(user.getId(), user.getFullName(), user.getUsername(), user.getRole());
    }

    public User toUser(UserCreationDTO userDTO) {
        String passwordHash = userDTO.getPassword();
        return new User(userDTO.getFullName(), userDTO.getUserName(), passwordHash, Roles.valueOf(userDTO.getRole()));
    }
}
