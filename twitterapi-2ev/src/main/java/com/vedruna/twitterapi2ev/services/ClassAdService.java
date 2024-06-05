package com.vedruna.twitterapi2ev.services;

import com.vedruna.twitterapi2ev.dto.ClassAdDTO;
import com.vedruna.twitterapi2ev.persistence.model.ClassAd;
import com.vedruna.twitterapi2ev.persistence.model.User;
import com.vedruna.twitterapi2ev.persistence.repository.ClassAdRepository;
import com.vedruna.twitterapi2ev.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassAdService implements ClassAdServiceI {


    @Autowired
    private ClassAdRepository classAdRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public ClassAdDTO createClassAd(ClassAdDTO classAdDto) {
        ClassAd classAd = convertToEntity(classAdDto);
        classAd = classAdRepository.save(classAd);
        return convertToDto(classAd);
    }

    @Override
    public ClassAdDTO updateClassAd(Long id, ClassAdDTO classAdDto) {
        ClassAd classAd = classAdRepository.findById(id).orElseThrow(() -> new RuntimeException("ClassAd not found"));
        classAd.setTitle(classAdDto.getTitle());
        classAd.setDescription(classAdDto.getDescription());
        classAd.setPrice(classAdDto.getPrice());
        classAd = classAdRepository.save(classAd);
        return convertToDto(classAd);
    }

    @Override
    public void deleteClassAd(Long id) {
        Optional<ClassAd> optionalClassAd = classAdRepository.findById(id);
        ClassAd classAdDelete = optionalClassAd.get();
        classAdRepository.deleteById(id);
    }

    @Override
    public List<ClassAdDTO> searchClassAdsByTitle(String title) {
        // Buscar anuncios de clase que contengan el título ignorando mayúsculas y minúsculas
        List<ClassAd> classAds = classAdRepository.findByTitleContainingIgnoreCase(title);

        // Convertir la lista de anuncios de clase a DTO y mapear los atributos necesarios
        return classAds.stream()
                .map(classAd -> new ClassAdDTO(
                        classAd.getId(),
                        classAd.getTitle(),
                        classAd.getDescription(),
                        classAd.getPrice(),
                        classAd.getUser().getId(),
                        classAd.getLikedByUsers().size() // Aquí puedes adaptar el mapeo según tus necesidades
                ))
                .collect(Collectors.toList());
    }


    @Override
    public List<ClassAdDTO> findAllClassAds() {
        return classAdRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ClassAdDTO findClassAdById(Long id) {
        ClassAd classAd = classAdRepository.findById(id).orElseThrow(() -> new RuntimeException("ClassAd not found"));
        return convertToDto(classAd);
    }

    @Override
    public void likeClassAd(Long userId, Long classAdId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ClassAd classAd = classAdRepository.findById(classAdId).orElseThrow(() -> new RuntimeException("ClassAd not found"));
        classAd.getLikedByUsers().add(user);
        classAdRepository.save(classAd);
    }

    @Override
    public void unlikeClassAd(Long userId, Long classAdId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ClassAd classAd = classAdRepository.findById(classAdId).orElseThrow(() -> new RuntimeException("ClassAd not found"));
        classAd.getLikedByUsers().remove(user);
        classAdRepository.save(classAd);
    }


    @Override
    public int countLikes(Long classAdId) {
        ClassAd classAd = classAdRepository.findById(classAdId).orElseThrow(() -> new RuntimeException("ClassAd not found"));
        return classAd.getLikedByUsers().size();
    }

    private ClassAdDTO convertToDto(ClassAd classAd) {
        return new ClassAdDTO(classAd.getId(), classAd.getTitle(), classAd.getDescription(), classAd.getPrice(), classAd.getUser().getId(), classAd.getLikedByUsers().size());
    }

    private ClassAd convertToEntity(ClassAdDTO classAdDto) {
        User user = userRepository.findById(classAdDto.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));
        return new ClassAd(classAdDto.getTitle(), classAdDto.getDescription(), classAdDto.getPrice(), user);
    }


    @Override
    public boolean isOwner(Long classAsId, Long userId) {
        // Obtener la publicación por su ID
        ClassAd classAd = classAdRepository.findById(classAsId).orElse(null);

        // Verificar si la publicación existe y si el ID del autor coincide con el ID del usuario autenticado
        return classAd != null && classAd.getUser().getId().equals(userId);
    }

    @Override
    public List<ClassAdDTO> findFavoriteClassAdsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFavoriteClassAds().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
