package br.edu.utfpr.agronomia.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlantingDTO {
    @NotBlank
    private String type;

    @NotBlank
    private String area;

    @NotBlank
    private WateringConditionsDTO wateringConditionsDTO;
}
