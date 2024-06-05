package com.vedruna.twitterapi2ev.services;

import com.vedruna.twitterapi2ev.dto.UserDTO;
import com.vedruna.twitterapi2ev.persistence.model.ClassAd;
import com.vedruna.twitterapi2ev.persistence.model.User;
import com.vedruna.twitterapi2ev.persistence.repository.ClassAdRepository;
import com.vedruna.twitterapi2ev.persistence.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;



@Service
public class UserService implements UserServiceI {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassAdRepository classAdRepository;

    @Override
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(hashPassword(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setDescription(user.getDescription());
        newUser.setCreateDate(LocalDate.now());
        newUser = userRepository.save(newUser);

        return newUser;  // Puedes decidir devolver la entidad o convertirla a un DTO adecuado para la respuesta
    }

    /**
     * Encuentra el nombre de usuario de un usuario dado su ID.
     *
     * @param userId El ID del usuario.
     * @return El nombre de usuario como un String o null si el usuario no existe.
     */
    public String getUsernameById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(User::getUsername).orElse(null);
    }

    /**
     * Encuentra el ID de un usuario dado su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario.
     * @return El ID del usuario como un Long o null si el usuario no existe.
     */
    public Long getUserIdByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getId).orElse(null);
    }

    @Override
    public Optional<UserDTO> findUserById(Long authenticatedUserId, Long id) {
        // Verificación de permisos puede ser necesaria aquí
        return userRepository.findById(id).map(this::convertToDto);
    }

    @Override
    public Optional<UserDTO> findByUsername(Long authenticatedUserId, String username) {
        // Verificación de permisos puede ser necesaria aquí
        return userRepository.findByUsername(username).map(this::convertToDto);
    }

    @Override
    public UserDTO updateUserDetails(Long authenticatedUserId, Map<String, Object> updateFields) {
        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Actualiza el username si está presente en el mapa
        if (updateFields.containsKey("username")) {
            String username = (String) updateFields.get("username");
            user.setUsername(username);
        }

        // Actualiza la descripción si está presente en el mapa
        if (updateFields.containsKey("description")) {
            String description = (String) updateFields.get("description");
            user.setDescription(description);
        }

        // Guarda los cambios en la base de datos
        user = userRepository.save(user);
        return convertToDto(user);
    }

    @Override
    public UserDTO changePassword(Long authenticatedUserId, String oldPassword, String newPassword) {
        User user = userRepository.findById(authenticatedUserId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password does not match");
        }
        user.setPassword(hashPassword(newPassword));
        userRepository.save(user);
        return convertToDto(user);
    }

    @Override
    public boolean addFavorite(Long userId, Long classAdId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ClassAd classAd = classAdRepository.findById(classAdId).orElseThrow(() -> new RuntimeException("ClassAd not found"));
        boolean added = user.getFavoriteClassAds().add(classAd);
        userRepository.save(user);
        return added;
    }

    @Override
    public boolean removeFavorite(Long userId, Long classAdId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ClassAd classAd = classAdRepository.findById(classAdId).orElseThrow(() -> new RuntimeException("ClassAd not found"));
        boolean removed = user.getFavoriteClassAds().remove(classAd);
        userRepository.save(user);
        return removed;
    }




    private UserDTO convertToDto(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail() ,user.getDescription(), user.getIdAvatar());
    }

    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}

