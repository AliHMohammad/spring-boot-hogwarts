package edu.hogwarts.springhogwarts.dto.student;

import edu.hogwarts.springhogwarts.models.House;
import edu.hogwarts.springhogwarts.models.Student;
import edu.hogwarts.springhogwarts.repositories.HouseRepository;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Optional;
import java.util.function.Function;

@ControllerAdvice
public class RequestStudentDTOMapper implements Function<RequestStudentDTO, Student> {

    private final HouseRepository houseRepository;

    public RequestStudentDTOMapper(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public Student apply(RequestStudentDTO requestStudentDTO) {
        Student s = new Student();

        s.setId(requestStudentDTO.id());
        s.setFullName(requestStudentDTO.fullName());
        s.setDateOfBirth(requestStudentDTO.dateOfBirth());
        s.setSchoolYear(requestStudentDTO.schoolYear());
        s.setPrefect(requestStudentDTO.prefect());
        s.setEnrollmentYear(requestStudentDTO.enrollmentYear());
        s.setGraduationYear(requestStudentDTO.graduationYear());
        s.setGraduated(requestStudentDTO.graduated());

        if (requestStudentDTO.house() != null){
            Optional<House> house = houseRepository.findById(requestStudentDTO.house());

            if (house.isPresent()) {
                s.setHouse(house.get());
            } else {
                s.setHouse(null);
            }
        }

        return s;
    }
}
