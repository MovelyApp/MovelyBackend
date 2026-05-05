package br.movely.movelyapp.config;

import br.movely.movelyapp.model.User;
import br.movely.movelyapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> foundUser = repo.findByUsernameIgnoreCase(username);

        if (!foundUser.isPresent()) {
            foundUser = repo.findByEmailIgnoreCase(username);
        }

        User user = foundUser.orElseThrow(() -> new RuntimeException("User Not Found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
