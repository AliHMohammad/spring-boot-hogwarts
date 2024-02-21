package edu.hogwarts.springhogwarts.dto.house;

import java.util.List;

public record HouseDTO(
        String name,
        String founder,
        List<String> colors
) {
}
