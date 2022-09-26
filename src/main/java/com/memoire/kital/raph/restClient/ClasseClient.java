package com.memoire.kital.raph.restClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClasseClient {
    private String id;
    private String nom;
    private String mensualite;
    private NiveauClient niveau;
}
