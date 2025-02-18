package br.edu.utfpr.agronomia.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WateringConditionsDTO {
    @NotBlank
    private String minimumTemperature;

    @NotBlank
    private String maximumTemperature;

    @NotBlank
    private String minimumHumidity;

    @NotBlank
    private String maximumHumidity;

    @NotBlank
    private String idealSchedule;
}
