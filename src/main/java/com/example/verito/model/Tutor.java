package com.example.verito.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String area;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MaterialEstudio> materiales;

    // Constructor vacío
    public Tutor() {}

    // Constructor con parámetros
    public Tutor(String nombre, String area) {
        this.nombre = nombre;
        this.area = area;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<MaterialEstudio> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<MaterialEstudio> materiales) {
        this.materiales = materiales;
    }
}

