package br.edu.utfpr.agronomia.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthDTO {
    @NotBlank
    @Size(min = 4, max = 150)
    private String email;

    @NotBlank
    @Size(min = 4, max = 50)
    private String password;
}
