package com.example.verito.repository;

import com.example.verito.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Tutor findByNombre(String nombre);
}


