package com.vedruna.twitterapi2ev.services;

import com.vedruna.twitterapi2ev.dto.ClassAdDTO;

import java.util.List;

public interface ClassAdServiceI {
    ClassAdDTO createClassAd(ClassAdDTO classAdDto);
    ClassAdDTO updateClassAd(Long id, ClassAdDTO classAdDto);
    void deleteClassAd(Long id);
    List<ClassAdDTO> findAllClassAds();
    List<ClassAdDTO> searchClassAdsByTitle(String title);
    ClassAdDTO findClassAdById(Long id);
    void likeClassAd(Long userId, Long classAdId);
    int countLikes(Long classAdId);
     boolean isOwner(Long classAdId, Long userId);
    List<ClassAdDTO> findFavoriteClassAdsByUserId(Long userId);
    void unlikeClassAd(Long userId, Long classAdId);
}
