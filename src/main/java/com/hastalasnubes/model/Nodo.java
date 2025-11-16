package com.hastalasnubes.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employees")
public class Nodo {

    @Id
    private String id;

    private String titulo;

    private String cuerpo;
}
