package edu.hogwarts.springhogwarts.services;

import edu.hogwarts.springhogwarts.models.House;
import edu.hogwarts.springhogwarts.repositories.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> getAllHouses() {
        return null;
    }
}
