package com.example.verito.repository;

import com.example.verito.model.MaterialEstudio;
import com.example.verito.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialEstudioRepository extends JpaRepository<MaterialEstudio, Long> {
    // Método para obtener los materiales de estudio por tutor
    List<MaterialEstudio> findByTutor(Tutor tutor);
}
