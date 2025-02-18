package br.edu.utfpr.agronomia.Domain;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_planting")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Planting extends BasicEntity {
    @Column(nullable = false)
    private String type; // Exemplo: "Milho", "Soja", etc.

    @Column(nullable = false)
    private String area; // Área em hectares

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_watering_conditions", referencedColumnName = "id")
    private WateringConditions wateringConditions;
}

