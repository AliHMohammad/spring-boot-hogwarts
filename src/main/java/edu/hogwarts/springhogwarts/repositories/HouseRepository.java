package edu.hogwarts.springhogwarts.repositories;


import edu.hogwarts.springhogwarts.models.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, String> {
}
