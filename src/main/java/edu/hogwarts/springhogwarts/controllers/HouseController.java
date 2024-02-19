package edu.hogwarts.springhogwarts.controllers;

import edu.hogwarts.springhogwarts.dto.house.HouseDTO;
import edu.hogwarts.springhogwarts.services.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/houses")
public class HouseController {

    private HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }


    @GetMapping
    public ResponseEntity<List<HouseDTO>> getAllHouses() {
        return ResponseEntity.ok(houseService.getAllHouses());
    }

    @GetMapping("/{houseName}")
    public ResponseEntity<HouseDTO> getSingleHouse(@PathVariable("houseName") String name) {
        return ResponseEntity.ok(houseService.getSingleHouse(name));
    }
}
