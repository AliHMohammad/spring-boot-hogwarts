package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.dto.house.ResponseHouseDTO;
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

    public List<ResponseHouseDTO> getAllHouses() {
        List<House> houses = houseRepository.findAll();
        List<ResponseHouseDTO> responseHouseDTOS = houses.stream()
                .map(house -> new ResponseHouseDTO(
                        house.getName(),
                        house.getFounder(),
                        house.getColors().stream().map(c -> c.getName()).toList()
                )).toList();

        return responseHouseDTOS;
    }

    public ResponseHouseDTO getSingleHouse(String name) {
        House house = houseRepository.findById(name)
                .orElseThrow(() -> new EntityNotFoundException("Could not find House by name " + name));

        return new ResponseHouseDTO(
                house.getName(),
                house.getFounder(),
                house.getColors().stream().map(c -> c.getName()).toList()
        );
    }
}
