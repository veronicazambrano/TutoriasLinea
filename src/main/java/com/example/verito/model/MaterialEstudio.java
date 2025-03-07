package com.example.verito.model;

import jakarta.persistence.*;

@Entity
public class MaterialEstudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String nombreArchivo;

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    private String rutaArchivo;


    // Almacenamos el archivo como un arreglo de bytes
    @Lob
    private byte[] archivo;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    // Constructor vacío necesario para JPA
    public MaterialEstudio() {
    }

    // Constructor con parámetros
    public MaterialEstudio(String titulo, String nombreArchivo, byte[] archivo, Tutor tutor) {
        this.titulo = titulo;
        this.nombreArchivo = nombreArchivo;
        this.archivo = archivo;
        this.tutor = tutor;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}
