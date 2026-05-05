package br.movely.movelyapp.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private Long userId;
    private float weight = -1;
    private float height = -1;
    private String email = null;
}
