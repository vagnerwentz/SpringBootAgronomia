package br.edu.utfpr.agronomia.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_wateringConditions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WateringConditions extends BasicEntity {
    @Column(nullable = false)
    private String minimumTemperature; // Exemplo: 18°C

    @Column(nullable = false)
    private String maximumTemperature; // Exemplo: 30°C

    @Column(nullable = false)
    private String minimumHumidity; // Exemplo: 50%

    @Column(nullable = false)
    private String maximumHumidity; // Exemplo: 80%

    @Column(nullable = false)
    private String idealSchedule; // Exemplo: "06:00-10:00"
}
