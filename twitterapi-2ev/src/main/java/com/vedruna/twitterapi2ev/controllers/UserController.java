package com.vedruna.twitterapi2ev.controllers;

import com.vedruna.twitterapi2ev.dto.ChangePasswordDTO;
import com.vedruna.twitterapi2ev.dto.UserDTO;
import com.vedruna.twitterapi2ev.jwt.JwtService;
import com.vedruna.twitterapi2ev.services.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserServiceI userService;
    @Autowired
    private JwtService jwtService;


    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
        Long authenticatedUserId = jwtService.getUserIdFromToken(SecurityContextHolder.getContext().getAuthentication().getName());
        Optional<UserDTO> userDTO = userService.findUserById(authenticatedUserId, id);
        return userDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getUsernameById/{id}")
    public ResponseEntity<String> getUsernameById(@PathVariable Long id) {
        String username = userService.getUsernameById(id);
        return username != null ? ResponseEntity.ok(username) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getUserIdByUsername/{username}")
    public ResponseEntity<Long> getUserIdByUsername(@PathVariable String username) {
        Long userId = userService.getUserIdByUsername(username);
        return userId != null ? ResponseEntity.ok(userId) : ResponseEntity.notFound().build();
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
        Long authenticatedUserId = jwtService.getUserIdFromToken(SecurityContextHolder.getContext().getAuthentication().getName());
        Optional<UserDTO> userDTO = userService.findByUsername(authenticatedUserId, username);
        return userDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Actualiza detalles del usuario autenticado basado en los campos proporcionados.
     *
     * @param updates Mapa de los campos a actualizar.
     * @return ResponseEntity con el DTO actualizado del usuario.
     */
    @PatchMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody Map<String, Object> updates) {
        Long authenticatedUserId = jwtService.getUserIdFromToken(
                SecurityContextHolder.getContext().getAuthentication().getName());
        UserDTO updatedUser = userService.updateUserDetails(authenticatedUserId, updates);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDto) {
        Long authenticatedUserId = jwtService.getUserIdFromToken(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.changePassword(authenticatedUserId, changePasswordDto.getOldPassword(), changePasswordDto.getNewPassword());
        return ResponseEntity.ok("Password updated successfully");
    }

    @PostMapping("/addFavorite/{classAdId}")
    public ResponseEntity<String> addFavorite(@PathVariable Long classAdId) {
        Long authenticatedUserId = jwtService.getUserIdFromToken(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            boolean added = userService.addFavorite(authenticatedUserId, classAdId);
            return added ? ResponseEntity.ok("ClassAd added to favorites") : ResponseEntity.badRequest().body("Failed to add ClassAd to favorites");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or ClassAd not found");
        }
    }

    @DeleteMapping("/removeFavorite/{classAdId}")
    public ResponseEntity<String> removeFavorite(@PathVariable Long classAdId) {
        Long authenticatedUserId = jwtService.getUserIdFromToken(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            boolean removed = userService.removeFavorite(authenticatedUserId, classAdId);
            return removed ? ResponseEntity.ok("ClassAd removed from favorites") : ResponseEntity.badRequest().body("Failed to remove ClassAd from favorites");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or ClassAd not found");
        }
    }

}


