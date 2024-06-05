package com.vedruna.twitterapi2ev.persistence.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "class_ads")
public class ClassAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;  // Relación con la entidad User

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "class_ad_likes",
            joinColumns = @JoinColumn(name = "class_ad_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likedByUsers;  // Lista de usuarios que han dado "like"

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "class_ad_id")
    )
    private Set<ClassAd> favoriteClassAds = new HashSet<>();

    public ClassAd(String title, String description, Double price, User user) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.user = user;
        this.likedByUsers = new ArrayList<>();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructores, getters y setters

    public ClassAd() {}

    public ClassAd(Long id, String title, String description, Double price, User user, List<User> likedByUsers, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.user = user;
        this.likedByUsers = likedByUsers;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ClassAd> getFavoriteClassAds() {
        return favoriteClassAds;
    }

    public void setFavoriteClassAds(Set<ClassAd> favoriteClassAds) {
        this.favoriteClassAds = favoriteClassAds;
    }

    public List<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(List<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    // Método para añadir un usuario a los likes
    public void addLike(User user) {
        this.likedByUsers.add(user);
        user.getLikedClassAds().add(this);
    }
}
