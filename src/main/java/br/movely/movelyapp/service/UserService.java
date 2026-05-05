package br.movely.movelyapp.service;

import br.movely.movelyapp.DTO.UpdateUserRequest;
import br.movely.movelyapp.DTO.UserDTO;
import br.movely.movelyapp.exceptions.ForbiddenException;
import br.movely.movelyapp.model.User;
import br.movely.movelyapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO getUser(Long userId) {
        return UserDTO.get(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found")));
    }

    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream().map(UserDTO::get).toList();
    }

    public UserDTO updateUser(UpdateUserRequest request, String username) {
        if (request == null || request.getUserId() == null) {
            throw new RuntimeException("User Id is required");
        }
        Long userId = request.getUserId();
        User actualUser = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not Found"));
        if (!userId.equals(actualUser.getId()) && !actualUser.getRole().equals("Admin")) {
            throw new ForbiddenException();
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (request.getEmail() == null && request.getHeight() < 0 && request.getWeight() < 0) {
            throw new RuntimeException("Please fill up at least one field");
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getWeight() >= 0) {
            user.setWeight(request.getWeight());
        }
        if (request.getHeight() >= 0) {
            user.setHeight(request.getHeight());
        }
        userRepository.save(user);
        return UserDTO.get(user);
    }

    public void deleteUser(Long userId, String username) {
        User actualUser = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (!userId.equals(actualUser.getId()) && !actualUser.getRole().equals("Admin")) {
            throw new ForbiddenException();
        }
        userRepository.deleteById(userId);
    }

}
