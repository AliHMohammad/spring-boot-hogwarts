package edu.hogwarts.springhogwarts.services;


import edu.hogwarts.springhogwarts.models.Teacher;
import edu.hogwarts.springhogwarts.repositories.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;


    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }




    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getSingleTeacher(long id) {
        return teacherRepository.findById(id);
    }

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }


    public Optional<Teacher> deleteTeacher(long id) {
        Optional<Teacher> teacherInDb = teacherRepository.findById(id);

        if (teacherInDb.isPresent()) {
            teacherRepository.delete(teacherInDb.get());
        }

        return teacherInDb;
    }

    @Transactional
    public Optional<Teacher> updateTeacher(long id, Teacher teacher) {
        Optional<Teacher> teacherInDb = teacherRepository.findById(id);

        if (teacherInDb.isEmpty()) return teacherInDb;

        teacherInDb.get().setFullName(teacher.getFullName());
        teacherInDb.get().setDateOfBirth(teacher.getDateOfBirth());
        teacherInDb.get().setEmployment(teacher.getEmployment());
        teacherInDb.get().setHouse(teacher.getHouse());
        teacherInDb.get().setHeadOfHouse(teacher.isHeadOfHouse());
        teacherInDb.get().setEmploymentStart(teacher.getEmploymentStart());
        teacherInDb.get().setEmploymentEnd(teacher.getEmploymentEnd());

        return teacherInDb;
    }
}
