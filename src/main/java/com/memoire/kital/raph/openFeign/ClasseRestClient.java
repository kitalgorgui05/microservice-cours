package com.memoire.kital.raph.openFeign;

import com.memoire.kital.raph.restClient.ClasseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "classe" , url = "http://localhost:8086/")
public interface ClasseRestClient {
    @GetMapping("/api/classes")
    public ResponseEntity<List<ClasseClient>> getAllClasses();
    @GetMapping("/api/classes/{id}")
    public ResponseEntity<ClasseClient> getClasse(@PathVariable("id") String id);
}
