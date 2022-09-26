package com.memoire.kital.raph.openFeign;

import com.memoire.kital.raph.restClient.ClasseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "classe")
public interface ClasseRestClient {

    @PostMapping("/api/classes")
     ClasseClient createClasse(@Valid @RequestBody ClasseClient classeDTO);

    @PutMapping( "/Api/classes")
    ClasseClient updateClasse(@Valid @RequestBody ClasseClient classeDTO);

    @GetMapping( "/api/classes")
    List<ClasseClient> getAllClasses();

    @GetMapping("/api/classes/{id}")
    ClasseClient getClasse(@PathVariable String id);
}
