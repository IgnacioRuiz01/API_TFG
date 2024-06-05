package com.vedruna.twitterapi2ev.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "description")
    private String description;

    @Column(name = "idAvatar")
    private int idAvatar;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "edit_date")
    private LocalDate editDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "class_ad_id")
    )
    private Set<ClassAd> likedClassAds = new HashSet<>();  // Set of ClassAds liked by the user

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClassAd> classAds;  // List of ClassAds posted by the user

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_favorites", // Cambia el nombre de la tabla intermedia para reflejar su uso
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "class_ad_id")
    )
    private Set<ClassAd> favoriteClassAds = new HashSet<>(); // Correctamente nombrada para favoritos





    // Getters and Setters
    public List<ClassAd> getClassAds() {
        return classAds;
    }

    public void setClassAds(List<ClassAd> classAds) {
        this.classAds = classAds;
    }

    public Set<ClassAd> getFavoriteClassAds() {
        return favoriteClassAds;
    }

    public void setFavoriteClassAds(Set<ClassAd> favoritedByUsers) {
        this.favoriteClassAds = favoritedByUsers;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}