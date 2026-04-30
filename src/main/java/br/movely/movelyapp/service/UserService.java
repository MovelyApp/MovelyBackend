package br.movely.movelyapp.service;

import br.movely.movelyapp.model.User;
import br.movely.movelyapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

}
