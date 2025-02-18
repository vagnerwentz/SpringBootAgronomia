package br.edu.utfpr.agronomia.Repository;

import br.edu.utfpr.agronomia.Domain.WateringConditions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WateringConditionsRepository  extends JpaRepository<WateringConditions, UUID> {
}
