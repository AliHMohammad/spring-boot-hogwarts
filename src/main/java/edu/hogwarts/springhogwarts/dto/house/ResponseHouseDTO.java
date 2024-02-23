package edu.hogwarts.springhogwarts.dto.house;

import java.util.List;

public record ResponseHouseDTO(
        String name,
        String founder,
        List<String> colors
) {
}
