package com.vedruna.twitterapi2ev.controllers;

import com.vedruna.twitterapi2ev.dto.ClassAdDTO;
import com.vedruna.twitterapi2ev.jwt.JwtService;
import com.vedruna.twitterapi2ev.services.ClassAdServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/classAds")
public class ClassAdController {

    @Autowired
    private ClassAdServiceI classAdService;
    @Autowired
    private JwtService jwtService;


    @PostMapping("/create")
    public ResponseEntity<ClassAdDTO> createClassAd(@RequestBody ClassAdDTO classAdDto) {
        Long authenticatedUserId = jwtService.getUserIdFromToken(SecurityContextHolder.getContext().getAuthentication().getName());
        ClassAdDTO newClassAd = classAdService.createClassAd(classAdDto);
        return ResponseEntity.ok(newClassAd);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClassAdDTO> updateClassAd(@PathVariable Long id, @RequestBody ClassAdDTO classAdDto) {
        ClassAdDTO updatedClassAd = classAdService.updateClassAd(id, classAdDto);
        return ResponseEntity.ok(updatedClassAd);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClassAd(@PathVariable Long id) {
        // Obtener el nombre de usuario del token JWT
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Obtener el ID del usuario del token JWT
        Long authenticatedUserId = jwtService.getUserIdFromToken(username);
        if (!classAdService.isOwner(id,authenticatedUserId)){

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para borrar esta publicación");

        }
        // Si el usuario autenticado es el propietario de la publicación, permitir la eliminación
        classAdService.deleteClassAd(id);
        return ResponseEntity.ok("Publicación borrada con éxito");
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<ClassAdDTO>> searchClassAdsByTitle(@PathVariable String title) {
        // Buscar anuncios de clase por título
        List<ClassAdDTO> classAds = classAdService.searchClassAdsByTitle(title);

        // Devolver la respuesta con los anuncios encontrados
        return ResponseEntity.ok(classAds);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ClassAdDTO>> findAllClassAds() {
        List<ClassAdDTO> classAds = classAdService.findAllClassAds();
        return ResponseEntity.ok(classAds);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ClassAdDTO> findClassAdById(@PathVariable Long id) {
        ClassAdDTO classAd = classAdService.findClassAdById(id);
        return ResponseEntity.ok(classAd);
    }

    @PostMapping("/like/{classAdId}")
    public ResponseEntity<Void> likeClassAd(@PathVariable Long classAdId) {
        Long userId = jwtService.getUserIdFromToken(SecurityContextHolder.getContext().getAuthentication().getName());
        classAdService.likeClassAd(userId, classAdId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unlike/{classAdId}")
    public ResponseEntity<Void> unlikeClassAd(@PathVariable Long classAdId) {
        Long userId = jwtService.getUserIdFromToken(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            classAdService.unlikeClassAd(userId, classAdId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/countLikes/{classAdId}")
    public ResponseEntity<Integer> countLikes(@PathVariable Long classAdId) {
        int likes = classAdService.countLikes(classAdId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<ClassAdDTO>> getFavoriteClassAds() {
        try {
            // Obtener el ID del usuario autenticado del token JWT
            Long authenticatedUserId = jwtService.getUserIdFromToken(SecurityContextHolder.getContext().getAuthentication().getName());

            // Usar el ID autenticado para buscar los anuncios favoritos
            List<ClassAdDTO> favorites = classAdService.findFavoriteClassAdsByUserId(authenticatedUserId);
            return ResponseEntity.ok(favorites);
        } catch (RuntimeException e) {
            // Manejar el caso en el que el usuario no es encontrado o el token es inválido
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<>());
        }
    }

}
