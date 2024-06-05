package com.vedruna.twitterapi2ev.persistence.repository;

import com.vedruna.twitterapi2ev.persistence.model.ClassAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface ClassAdRepository extends JpaRepository<ClassAd, Long> {
    // Encuentra anuncios de clases por el ID del usuario que los publicó
    List<ClassAd> findByUserId(Long userId);

    // Encuentra anuncios de clases que contengan en el título o descripción un término específico (búsqueda básica)
    List<ClassAd> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);

    // Método para buscar anuncios por título, ignorando mayúsculas y minúsculas
    // Busca películas que contengan una palabra clave en el título
    List<ClassAd> findByTitleContaining(String keyword);
    List<ClassAd> findByTitleContainingIgnoreCase(String title);
}
