package br.com.albumfigurinha.api.service.impl;

import br.com.albumfigurinha.api.dto.UserCreationDTO;
import br.com.albumfigurinha.api.dto.UserDTO;
import br.com.albumfigurinha.api.entity.User;
import br.com.albumfigurinha.api.entity.enums.Roles;
import br.com.albumfigurinha.api.exception.UserAlreadyExistsException;
import br.com.albumfigurinha.api.exception.UserNotFoundException;
import br.com.albumfigurinha.api.mapper.UserMapper;
import br.com.albumfigurinha.api.repository.UserRepository;
import br.com.albumfigurinha.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    public UserDTO createUser(UserCreationDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        var existingUserWithGivenUserName = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (existingUserWithGivenUserName != null) {
            throw new UserAlreadyExistsException("User already exists with the given userName: { " + user.getUsername() + " }");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public UserDTO getUserById(String userId) {
        return userMapper.toDto(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with the given ID: { " + userId + " }")));
    }

    public UserDTO resetPasswordByUserId(String userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with the given ID: { " + userId + " }"));

        String encryptedPassword = new BCryptPasswordEncoder().encode(existingUser.getUsername());
        existingUser.setPassword(encryptedPassword);

        userRepository.save(existingUser);
        return userMapper.toDto(existingUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(toList());
    }

    public UserDTO updateUser(UserCreationDTO userDTO, String userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with the given ID: { " + userId + " }"));
        var existingUserWithGivenUserName = userRepository.findByUsername(userDTO.getUserName()).orElse(null);
        if (existingUserWithGivenUserName != null && !userDTO.getUserName().equals(existingUserWithGivenUserName.getUsername())) {
            throw new UserAlreadyExistsException("User already exists with the given userName: { " + userDTO.getUserName() + " }");
        }
        if (!userDTO.getUserName().isBlank() && !userDTO.getUserName().isEmpty()) {
            existingUser.setUsername(userDTO.getUserName());
        }
        if (!userDTO.getFullName().isBlank() && !userDTO.getFullName().isEmpty()) {
            existingUser.setFullName(userDTO.getFullName());
        }
        if (!userDTO.getPassword().isBlank() && !userDTO.getPassword().isEmpty()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
            existingUser.setPassword(encryptedPassword);
        }
        if (Roles.isValidRole(userDTO.getRole())) {
            existingUser.setRole(Roles.valueOf(userDTO.getRole()));
        }
        userRepository.save(existingUser);
        return userMapper.toDto(existingUser);
    }

    public void deleteUser(String userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with the given ID: { " + userId + " }"));
        userRepository.deleteById(userId);
    }
}
