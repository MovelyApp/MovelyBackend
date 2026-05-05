package br.movely.movelyapp.service;

import br.movely.movelyapp.DTO.LoginRequest;
import br.movely.movelyapp.DTO.RegisterRequest;
import br.movely.movelyapp.DTO.UserDTO;
import br.movely.movelyapp.model.User;
import br.movely.movelyapp.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    JwtService jwtService;

    public void register(RegisterRequest request) {
        String accountEmail = request.getEmail() != null && !request.getEmail().trim().isEmpty()
                ? request.getEmail().trim().toLowerCase()
                : request.getUsername().trim().toLowerCase();

        if (userRepository.findByUsernameIgnoreCase(accountEmail).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        if (userRepository.findByEmailIgnoreCase(accountEmail).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(accountEmail);
        user.setEmail(accountEmail);
        user.setPassword(encoder.encode(request.getPassword()));

        userRepository.save(user);
    }


    public String login(LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails user = (UserDetails) auth.getPrincipal();

        try {
            return jwtService.generateToken(user.getUsername());
        } catch (JOSEException e) {
            throw new RuntimeException("Token generation failed");
        }

    }

    public UserDTO me(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDTO.get(user);
    }

}
