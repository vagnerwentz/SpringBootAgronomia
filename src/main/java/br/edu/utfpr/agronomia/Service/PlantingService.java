package br.edu.utfpr.agronomia.Service;

import br.edu.utfpr.agronomia.Domain.Planting;
import br.edu.utfpr.agronomia.Repository.PlantingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlantingService {

    @Autowired
    private PlantingRepository plantingRepository;

    public Planting save(Planting plantacao) {
        return plantingRepository.save(plantacao);
    }

    public Page<Planting> getAllPageable(Pageable pageable) {
        return plantingRepository.findAll(pageable);
    }

    public List<Planting> getAll() {
        return plantingRepository.findAll();
    }

    public Optional<Planting> getPlantingById(UUID id) {
        return plantingRepository.findById(id);
    }

    public void removePlanting(UUID id) {
        plantingRepository.deleteById(id);
    }
}
