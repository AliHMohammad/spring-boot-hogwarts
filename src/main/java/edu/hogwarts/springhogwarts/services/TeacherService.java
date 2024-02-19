package edu.hogwarts.springhogwarts.services;


import edu.hogwarts.springhogwarts.dto.teacher.TeacherDTO;
import edu.hogwarts.springhogwarts.dto.teacher.TeacherDTOMapper;
import edu.hogwarts.springhogwarts.models.Course;
import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherDTOMapper teacherDTOMapper;


    public TeacherService(TeacherRepository teacherRepository, TeacherDTOMapper teacherDTOMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherDTOMapper = teacherDTOMapper;
    }




    public List<TeacherDTO> getTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(teacherDTOMapper)
                .toList();
    }

    public TeacherDTO getSingleTeacher(long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Could not find teacher by id"));

        return teacherDTOMapper.apply(teacher);
    }

    public TeacherDTO createTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
        return teacherDTOMapper.apply(teacher);
    }


    public TeacherDTO deleteTeacher(long id) {
        Teacher teacherInDb = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        TeacherDTO teacherDTO = teacherDTOMapper.apply(teacherInDb);


        for (Course course: teacherInDb.getCourses()) {
            course.removeTeacher(teacherInDb);
        }

        teacherInDb.setHouse(null);
        teacherRepository.delete(teacherInDb);

        return teacherDTO;
    }

    @Transactional
    public TeacherDTO updateTeacher(long id, Teacher teacher) {
        Teacher teacherInDb = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find teacher by id"));

        teacherInDb.setFullName(teacher.getFullName());
        teacherInDb.setDateOfBirth(teacher.getDateOfBirth());
        teacherInDb.setEmployment(teacher.getEmployment());
        teacherInDb.setHouse(teacher.getHouse());
        teacherInDb.setHeadOfHouse(teacher.isHeadOfHouse());
        teacherInDb.setEmploymentStart(teacher.getEmploymentStart());
        teacherInDb.setEmploymentEnd(teacher.getEmploymentEnd());

        return teacherDTOMapper.apply(teacherInDb);
    }
}
