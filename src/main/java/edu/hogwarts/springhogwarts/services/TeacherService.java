package edu.hogwarts.springhogwarts.services;


import edu.hogwarts.springhogwarts.dto.teacher.ResponseTeacherDTO;
import edu.hogwarts.springhogwarts.dto.teacher.ResponseTeacherDTOMapper;
import edu.hogwarts.springhogwarts.dto.teacher.request.TeacherDTOEmployment;
import edu.hogwarts.springhogwarts.dto.teacher.request.TeacherDTOEmploymentEnd;
import edu.hogwarts.springhogwarts.dto.teacher.request.TeacherDTOHeadOfHouse;
import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ResponseTeacherDTOMapper responseTeacherDTOMapper;


    public TeacherService(TeacherRepository teacherRepository, ResponseTeacherDTOMapper responseTeacherDTOMapper) {
        this.teacherRepository = teacherRepository;
        this.responseTeacherDTOMapper = responseTeacherDTOMapper;
    }




    public List<ResponseTeacherDTO> getTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(responseTeacherDTOMapper)
                .toList();
    }

    public ResponseTeacherDTO getSingleTeacher(long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Could not find teacher by id"));

        return responseTeacherDTOMapper.apply(teacher);
    }

    public ResponseTeacherDTO createTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
        return responseTeacherDTOMapper.apply(teacher);
    }


    public ResponseTeacherDTO deleteTeacher(long id) {
        Teacher teacherInDb = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        ResponseTeacherDTO responseTeacherDTO = responseTeacherDTOMapper.apply(teacherInDb);


        for (Course course: teacherInDb.getCourses()) {
            course.removeTeacher(teacherInDb);
        }

        teacherInDb.setHouse(null);
        teacherRepository.delete(teacherInDb);

        return responseTeacherDTO;
    }

    @Transactional
    public ResponseTeacherDTO updateTeacher(long id, Teacher teacher) {
        Teacher teacherInDb = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find teacher by id"));

        teacherInDb.setFullName(teacher.getFullName());
        teacherInDb.setDateOfBirth(teacher.getDateOfBirth());
        teacherInDb.setEmployment(teacher.getEmployment());
        teacherInDb.setHouse(teacher.getHouse());
        teacherInDb.setHeadOfHouse(teacher.isHeadOfHouse());
        teacherInDb.setEmploymentStart(teacher.getEmploymentStart());
        teacherInDb.setEmploymentEnd(teacher.getEmploymentEnd());

        return responseTeacherDTOMapper.apply(teacherInDb);
    }

    public ResponseTeacherDTO updateTeacherHeadOfHouse(TeacherDTOHeadOfHouse teacherDTOHeadOfHouse, long id) {
        Teacher teacherInDb = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find teacher by id"));

        teacherInDb.setHeadOfHouse(teacherDTOHeadOfHouse.headOfHouse());

        teacherRepository.save(teacherInDb);
        return responseTeacherDTOMapper.apply(teacherInDb);
    }

    public ResponseTeacherDTO updateTeacherEmploymentEnd(TeacherDTOEmploymentEnd teacherDTOEmploymentEnd, long id) {
        Teacher teacherInDb = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find teacher by id"));

        teacherInDb.setEmploymentEnd(teacherDTOEmploymentEnd.employmentEnd());

        teacherRepository.save(teacherInDb);
        return responseTeacherDTOMapper.apply(teacherInDb);
    }

    public ResponseTeacherDTO updateTeacherEmployment(TeacherDTOEmployment teacherDTOEmployment, long id) {
        Teacher teacherInDb = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find teacher by id"));

        teacherInDb.setEmployment(teacherDTOEmployment.employment());

        teacherRepository.save(teacherInDb);
        return responseTeacherDTOMapper.apply(teacherInDb);
    }
}
