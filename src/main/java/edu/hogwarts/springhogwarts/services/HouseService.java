package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.dto.house.HouseDTO;
import edu.hogwarts.springhogwarts.models.House;
import edu.hogwarts.springhogwarts.repositories.HouseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<HouseDTO> getAllHouses() {
        List<House> houses = houseRepository.findAll();
        List<HouseDTO> houseDTOS = houses.stream()
                .map(house -> new HouseDTO(
                        house.getName(),
                        house.getFounder(),
                        house.getColors().stream().map(c -> c.getName()).toList()
                )).toList();

        return houseDTOS;
    }

    public HouseDTO getSingleHouse(String name) {
        House house = houseRepository.findById(name)
                .orElseThrow(() -> new EntityNotFoundException("Could not find House by name " + name));

        return new HouseDTO(
                house.getName(),
                house.getFounder(),
                house.getColors().stream().map(c -> c.getName()).toList()
        );
    }
}
