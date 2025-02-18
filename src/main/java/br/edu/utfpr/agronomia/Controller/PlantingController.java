package br.edu.utfpr.agronomia.Controller;

import br.edu.utfpr.agronomia.DTO.MessageDTO;
import br.edu.utfpr.agronomia.DTO.PlantingDTO;
import br.edu.utfpr.agronomia.Domain.Person;
import br.edu.utfpr.agronomia.Domain.Planting;
import br.edu.utfpr.agronomia.Domain.WateringConditions;
import br.edu.utfpr.agronomia.Service.PlantingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/planting")
public class PlantingController {

    @Autowired
    private PlantingService plantingService;

    /**
     * Endpoint para listar todas as plantações.
     * @return Lista de plantações.
     */
    @GetMapping("list")
    public ResponseEntity<List<Planting>> getAll() {
        List<Planting> plantings = plantingService.getAll();
        return ResponseEntity.ok(plantings);
    }

    /**
     * Endpoint para adicionar uma nova plantação.
     * @param plantingDTO Dados da nova plantação.
     * @return Plantação criada.
     */
    @PostMapping
    public ResponseEntity<Object> addPlanting(@RequestBody PlantingDTO plantingDTO) {
        var planting = new Planting();

        BeanUtils.copyProperties(plantingDTO, planting);
        if (plantingDTO.getWateringConditionsDTO() != null) {
            var wateringConditions = new WateringConditions();
            wateringConditions.setMinimumTemperature(plantingDTO.getWateringConditionsDTO().getMinimumTemperature());
            wateringConditions.setMaximumTemperature(plantingDTO.getWateringConditionsDTO().getMaximumTemperature());
            wateringConditions.setMinimumHumidity(plantingDTO.getWateringConditionsDTO().getMinimumHumidity());
            wateringConditions.setMaximumHumidity(plantingDTO.getWateringConditionsDTO().getMaximumHumidity());
            wateringConditions.setIdealSchedule(plantingDTO.getWateringConditionsDTO().getIdealSchedule());
            planting.setWateringConditions(wateringConditions);
        }

        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        planting.setCreatedAt(now);
        planting.setUpdatedAt(now);

        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(plantingService.save(planting));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageDTO.b(e.getMessage()));
        }
    }

    @GetMapping("list/filter")
    public ResponseEntity<Page<Planting>> getAllPageable(
            @PageableDefault(page = 0, size = 10, sort = "type", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(plantingService.getAllPageable(pageable));
    }

//    /**
//     * Endpoint para publicar as condições de todas as plantações no SNS.
//     * @return Mensagem de sucesso.
//     */
//    @PostMapping("/publicar-condicoes")
//    public ResponseEntity<String> publicarCondicoes() {
//        plantingService.publicarCondicoesPlantas();
//        return ResponseEntity.ok("Condições das plantações publicadas com sucesso no SNS!");
//    }

    /**
     * Endpoint para obter uma plantação específica pelo ID.
     * @param id Identificador da plantação.
     * @return Dados da plantação encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        Optional<Planting> planting = plantingService.getPlantingById(id);

        return planting.isPresent()
                ? ResponseEntity.ok(planting.get())
                : ResponseEntity.notFound().build();
    }

    /**
     * Endpoint para atualizar os dados de uma plantação e também as condições de rega.
     * @param id Identificador da plantação.
     * @param fields Dados atualizados.
     * @return Plantação atualizada.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updatePartial(@PathVariable UUID id, @RequestBody Map<String, Object> fields) {
        Optional<Planting> planting = plantingService.getPlantingById(id);

        if (planting.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(MessageDTO.b("Plantação deletada com sucesso!"));
        }


        // Atualiza os campos dinamicamente
            fields.forEach((campo, valor) -> {
            switch (campo) {
                case "type":
                    planting.ifPresent(p -> p.setType((String) valor));
                    break;
                case "area":
                    planting.ifPresent(p -> p.setArea(valor.toString()));
                    break;
                case "wateringConditionsDTO":
                    Map<String, Object> wcAtualizacoes = (Map<String, Object>) valor;
                    Optional<WateringConditions> wateringConditionsOptional = planting.map(Planting::getWateringConditions);
                    if (wateringConditionsOptional != null) {
                        wcAtualizacoes.forEach((wcCampo, wcValor) -> {
                            switch (wcCampo) {
                                case "minimumTemperature":
                                    wateringConditionsOptional.ifPresent(w -> w.setMinimumTemperature(wcValor.toString()));
                                    break;
                                case "maximumTemperature":
                                    wateringConditionsOptional.ifPresent(w -> w.setMaximumTemperature(wcValor.toString()));
                                    break;
                                case "minimumHumidity":
                                    wateringConditionsOptional.ifPresent(w -> w.setMinimumHumidity(wcValor.toString()));
                                    break;
                                case "maximumHumidity":
                                    wateringConditionsOptional.ifPresent(w -> w.setMaximumHumidity(wcValor.toString()));
                                    break;
                                case "idealSchedule":
                                    wateringConditionsOptional.ifPresent(w -> w.setIdealSchedule((String) wcValor));
                                    break;
                            }
                        });
                    }
                    break;
            }
        });

        planting.ifPresent(p -> p.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC"))));
        return ResponseEntity.ok(plantingService.save(planting.get()));
    }


    /**
     * Endpoint para deletar uma plantação pelo ID.
     * @param id Identificador da plantação.
     * @return Mensagem de sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removePlanting(@PathVariable UUID id) {
        var planting = plantingService.getPlantingById(id);

        if (planting.isEmpty())
            return ResponseEntity.notFound().build();

        plantingService.removePlanting(planting.get().getId());
        return ResponseEntity.ok("Plantação deletada com sucesso!");
    }
}
