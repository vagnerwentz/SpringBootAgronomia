package br.edu.utfpr.agronomia.Repository;

import br.edu.utfpr.agronomia.Domain.Planting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlantingRepository extends JpaRepository<Planting, UUID> {
}
