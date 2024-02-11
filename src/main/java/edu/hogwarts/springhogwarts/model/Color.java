package edu.hogwarts.springhogwarts.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "houses_colors",
            joinColumns = @JoinColumn(name = "color_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id")
    )
    Set<House> houses = new HashSet<>();

    public Color(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Color(String name) {
        this.name = name;
    }

    public Color() {
    }

    public Color(Color other) {
        this.name = other.name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<House> getHouses() {
        return houses;
    }
}
