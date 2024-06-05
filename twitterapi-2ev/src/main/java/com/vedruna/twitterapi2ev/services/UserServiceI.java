package com.vedruna.twitterapi2ev.services;

import com.vedruna.twitterapi2ev.dto.UserDTO;
import com.vedruna.twitterapi2ev.persistence.model.User;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.Optional;


@Service
public interface UserServiceI {

    User registerUser(User user);  // Cambiado para aceptar UserDTO y posiblemente devolver una entidad User o UserDTO.
    Optional<UserDTO> findUserById(Long authenticatedUserId, Long id); // Agregado el userId para verificar si el usuario tiene permiso para ver otro perfil.
    Optional<UserDTO> findByUsername(Long authenticatedUserId, String username); // Similar al anterior para manejar permisos.
    UserDTO updateUserDetails(Long authenticatedUserId, Map<String, Object> updateFields); // Necesita el userId para verificar la propiedad del perfil.
    UserDTO changePassword(Long authenticatedUserId, String oldPassword, String newPassword); // Asegura que solo el usuario autenticado pueda cambiar su contraseña.
    String getUsernameById(Long userId);
    Long getUserIdByUsername(String username);
    boolean addFavorite(Long userId, Long classAdId);
    boolean removeFavorite(Long userId, Long classAdId);


}

