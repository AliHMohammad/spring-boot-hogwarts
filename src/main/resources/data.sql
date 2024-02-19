/* COLORS */

INSERT INTO hogwarts_db.color (name) VALUES ('Green');
INSERT INTO hogwarts_db.color (name) VALUES ('Silver');
INSERT INTO hogwarts_db.color (name) VALUES ('Scarlet');
INSERT INTO hogwarts_db.color (name) VALUES ('Gold');
INSERT INTO hogwarts_db.color (name) VALUES ('Yellow');
INSERT INTO hogwarts_db.color (name) VALUES ('Black');
INSERT INTO hogwarts_db.color (name) VALUES ('Blue');
INSERT INTO hogwarts_db.color (name) VALUES ('Bronze');

/* HOUSES */

INSERT INTO hogwarts_db.house (founder, name) VALUES ( 'Salazar Slytherin', 'Slytherin');
INSERT INTO hogwarts_db.house (founder, name) VALUES ( 'Godric Gryffindor', 'Gryffindor');
INSERT INTO hogwarts_db.house (founder, name) VALUES ( 'Helga Huflepuff', 'HufflePuff');
INSERT INTO hogwarts_db.house (founder, name) VALUES ( 'Rowena Ravenclaw', 'Ravenclaw');


/* HOUSES_COLORS */

INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (1, 'Slytherin');
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (2, 'Slytherin');
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (3, 'Gryffindor');
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (4, 'Gryffindor');
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (5, 'HufflePuff');
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (6, 'HufflePuff');
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (7, 'Ravenclaw');
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (8, 'Ravenclaw');


/* STUDENTS */

INSERT INTO hogwarts_db.student ( house, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES ('Slytherin', 'Vincent', null, 'Crabbe', '1980-09-01', 1991, 1998, false, true);
INSERT INTO hogwarts_db.student ( house, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES ('Gryffindor', 'Neville', null, 'Longbottom', '1980-07-30', 1991, 1998, true, true);
INSERT INTO hogwarts_db.student ( house, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES ('Gryffindor', 'Lavender', null, 'Brown', '1979-01-09', 1991, 1998, false, false);
INSERT INTO hogwarts_db.student ( house, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES ('Gryffindor', 'Hermione', null, 'Granger', '1979-09-19', 1991, 1998, true, true);
INSERT INTO hogwarts_db.student ( house, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES ('Gryffindor', 'Harry', null, 'Potter', '1980-07-31', 1991, 1998, true, true);
INSERT INTO hogwarts_db.student ( house, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES ('HufflePuff', 'Susan', null, 'Bones', '1979-09-15', 1991, 1998, true, false);
INSERT INTO hogwarts_db.student ( house, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES ('HufflePuff', 'Hannah', null, 'Abbott', '1979-09-01', 1991, 1998, true, false);
INSERT INTO hogwarts_db.student ( house, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES ('Ravenclaw', 'Sue', null, 'Li', '1979-09-30', 1991, 1998, true, true);
INSERT INTO hogwarts_db.student ( house, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES ('Ravenclaw', 'Michael', null, 'Cornor', '1979-09-21', 1991, 1998, true, false);
INSERT INTO hogwarts_db.student ( house, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES ('Ravenclaw', 'Terry', null, 'Boot', '1980-04-21', 1991, 1998, true, false);

/* TEACHERS */

INSERT INTO hogwarts_db.teacher (date_of_birth, employment_end, employment_start, head_of_house, id, first_name, house, last_name, middle_name, employment)
VALUES ('1913-04-28', null, '2000-12-12', false, 1, 'Horace', 'Slytherin', 'Slughorn', null, 'Deceased');
INSERT INTO hogwarts_db.teacher (date_of_birth, employment_end, employment_start, head_of_house, id, first_name, house, last_name, middle_name, employment) VALUES ('1960-01-09', '1990-10-13', '1980-12-22', true, 2, 'Severus', 'Slytherin', 'Snape', null, 'Discharged');
INSERT INTO hogwarts_db.teacher (date_of_birth, employment_end, employment_start, head_of_house, id, first_name, house, last_name, middle_name, employment) VALUES ('1881-08-16', null, '1966-02-12', false, 3, 'Albus', 'Gryffindor', 'Dumbledore', 'Percival Wulfric Brian', 'Tenured');
INSERT INTO hogwarts_db.teacher (date_of_birth, employment_end, employment_start, head_of_house, id, first_name, house, last_name, middle_name, employment) VALUES ('1890-10-04', null, '1950-06-22', true, 4, 'Minerva', 'Gryffindor', 'McGonegall', null, 'Temporary');
INSERT INTO hogwarts_db.teacher (date_of_birth, employment_end, employment_start, head_of_house, id, first_name, house, last_name, middle_name, employment) VALUES ('1910-05-15', null, '1955-10-05', true, 5, 'Pomona', 'HufflePuff', 'Sprout', null, 'Probation');
INSERT INTO hogwarts_db.teacher (date_of_birth, employment_end, employment_start, head_of_house, id, first_name, house, last_name, middle_name, employment) VALUES ('1958-10-17', null, '1978-11-20', true, 6, 'Filius', 'Ravenclaw', 'Flitwick', null, 'Probation');

/* COURSES */

INSERT INTO hogwarts_db.course (teacher_id, subject, schoolyear, current) VALUES (2, 'Magic', 1991, true);
INSERT INTO hogwarts_db.course (teacher_id, subject, schoolyear, current) VALUES (4, 'Discipline', 1995, true);

/* STUDENTS_COURSES */

INSERT INTO hogwarts_db.students_courses (student_id, course_id) VALUES (2, 1);
INSERT INTO hogwarts_db.students_courses (student_id, course_id) VALUES (2, 2);
INSERT INTO hogwarts_db.students_courses (student_id, course_id) VALUES (3, 1);
INSERT INTO hogwarts_db.students_courses (student_id, course_id) VALUES (5, 1);
INSERT INTO hogwarts_db.students_courses (student_id, course_id) VALUES (5, 2);
INSERT INTO hogwarts_db.students_courses (student_id, course_id) VALUES (1, 1);
INSERT INTO hogwarts_db.students_courses (student_id, course_id) VALUES (1, 2);
INSERT INTO hogwarts_db.students_courses (student_id, course_id) VALUES (6, 2);
