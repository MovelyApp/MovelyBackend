package br.movely.movelyapp.DTO;

import br.movely.movelyapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String username;
    private String role;
    private float weight;
    private float height;
    private LocalDateTime createdAt;

    public static UserDTO get(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        userDto.setWeight(user.getWeight());
        userDto.setHeight(user.getHeight());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }
}
