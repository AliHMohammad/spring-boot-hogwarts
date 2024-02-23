package edu.hogwarts.springhogwarts.dto.teacher;

import edu.hogwarts.springhogwarts.models.EmploymentType;
import edu.hogwarts.springhogwarts.models.House;
import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.repositories.HouseRepository;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Optional;
import java.util.function.Function;

@ControllerAdvice
public class RequestTeacherDTOMapper implements Function<RequestTeacherDTO, Teacher> {

    private final HouseRepository houseRepository;

    public RequestTeacherDTOMapper(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public Teacher apply(RequestTeacherDTO requestTeacherDTO) {
        Teacher t = new Teacher();
        t.setId(requestTeacherDTO.id());
        t.setFullName(requestTeacherDTO.fullName());
        t.setDateOfBirth(requestTeacherDTO.dateOfBirth());
        t.setHeadOfHouse(requestTeacherDTO.headOfHouse());
        t.setEmployment(EmploymentType.valueOf(requestTeacherDTO.employment()));
        t.setEmploymentStart(requestTeacherDTO.employmentStart());
        t.setEmploymentEnd(requestTeacherDTO.employmentEnd());


        if (requestTeacherDTO.house() != null){
            Optional<House> house = houseRepository.findById(requestTeacherDTO.house());

            if (house.isPresent()) {
                t.setHouse(house.get());
            } else {
                t.setHouse(null);
            }
        }

        return t;
    }
}
