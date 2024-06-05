package com.vedruna.twitterapi2ev.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String description;
    private int idAvatar;


    public UserDTO() {

    }

    public UserDTO(Long id, String username, String email ,String description, int idAvatar) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.description = description;
        this.idAvatar = idAvatar;

    }

    public UserDTO( int idAvatar, String username, String email,String description) {
        this.idAvatar = idAvatar;
        this.username = username;
        this.email = email;
        this.description = description;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getidAvatar() {
        return idAvatar;
    }

    public void setidAvatar(int idAvatar) {
        this.idAvatar = idAvatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
