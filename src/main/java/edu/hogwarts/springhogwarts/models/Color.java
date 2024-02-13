package edu.hogwarts.springhogwarts.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany()
    @JoinTable(
            name = "houses_colors",
            joinColumns = @JoinColumn(name = "color_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id")
    )
    Set<House> houses = new HashSet<>();

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
