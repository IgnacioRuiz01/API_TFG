package com.vedruna.twitterapi2ev.dto;

import lombok.Data;

@Data
public class ClassAdDTO {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private Long user_id;  // Nombre del usuario que publica el anuncio
    private int likeCount;  // Número de likes

    public ClassAdDTO() {}

    public ClassAdDTO(Long id, String title, String description, Double price, Long user_id, int likeCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.user_id = user_id;
        this.likeCount = likeCount;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}

