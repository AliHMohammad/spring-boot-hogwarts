/* COLORS */

INSERT INTO hogwarts_db.color (id, name) VALUES (1, 'Green');
INSERT INTO hogwarts_db.color (id, name) VALUES (2, 'Silver');
INSERT INTO hogwarts_db.color (id, name) VALUES (3, 'Scarlet');
INSERT INTO hogwarts_db.color (id, name) VALUES (4, 'Gold');
INSERT INTO hogwarts_db.color (id, name) VALUES (5, 'Yellow');
INSERT INTO hogwarts_db.color (id, name) VALUES (6, 'Black');
INSERT INTO hogwarts_db.color (id, name) VALUES (7, 'Blue');
INSERT INTO hogwarts_db.color (id, name) VALUES (8, 'Bronze');

/* HOUSES */

INSERT INTO hogwarts_db.house (id, founder, name) VALUES (1, 'Salazar Slytherin', 'Slytherin');
INSERT INTO hogwarts_db.house (id, founder, name) VALUES (2, 'Godric Gryffindor', 'Gryffindor');
INSERT INTO hogwarts_db.house (id, founder, name) VALUES (3, 'Helga Huflepuff', 'HufflePuff');
INSERT INTO hogwarts_db.house (id, founder, name) VALUES (4, 'Rowena Ravenclaw', 'Ravenclaw');


/* HOUSES_COLORS */

INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (1, 1);
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (2, 1);
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (3, 2);
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (4, 2);
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (5, 3);
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (6, 3);
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (7, 4);
INSERT INTO hogwarts_db.houses_colors (color_id, house_id) VALUES (8, 4);


/* STUDENTS */

INSERT INTO hogwarts_db.student (id, house_id, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES (13, 1, 'Vincent', null, 'Crabbe', '1980-09-01', 1991, 1998, false, true);
INSERT INTO hogwarts_db.student (id, house_id, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES (12, 4, 'Terry', null, 'Boot', '1980-04-21', 1991, 1998, true, false);
INSERT INTO hogwarts_db.student (id, house_id, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES (9, 3, 'Susan', null, 'Bones', '1979-09-15', 1991, 1998, true, false);
INSERT INTO hogwarts_db.student (id, house_id, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES (11, 4, 'Sue', null, 'Li', '1979-09-30', 1991, 1998, true, true);
INSERT INTO hogwarts_db.student (id, house_id, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES (7, 2, 'Neville', null, 'Longbottom', '1980-07-30', 1991, 1998, true, true);
INSERT INTO hogwarts_db.student (id, house_id, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES (10, 4, 'Michael', null, 'Cornor', '1979-09-21', 1991, 1998, true, false);
INSERT INTO hogwarts_db.student (id, house_id, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES (3, 2, 'Lavender', null, 'Brown', '1979-01-09', 1991, 1998, false, false);
INSERT INTO hogwarts_db.student (id, house_id, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES (6, 2, 'Hermione', null, 'Granger', '1979-09-19', 1991, 1998, true, true);
INSERT INTO hogwarts_db.student (id, house_id, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES (2, 2, 'Harry', null, 'Potter', '1980-07-31', 1991, 1998, true, true);
INSERT INTO hogwarts_db.student (id, house_id, first_name, middle_name, last_name, date_of_birth, enrollment_year, graduation_year, graduated, prefect) VALUES (8, 3, 'Hannah', null, 'Abbott', '1979-09-01', 1991, 1998, true, false);


/* TEACHERS */

INSERT INTO hogwarts_db.teacher (id, house_id, first_name, middle_name, last_name, date_of_birth, employment, employment_start, employment_end, head_of_house) VALUES (1, 2, 'Albus', 'Percival Wulfric Brian', 'Dumbledore', '1881-08-16', null, '1966-02-12', null, false);
INSERT INTO hogwarts_db.teacher (id, house_id, first_name, middle_name, last_name, date_of_birth, employment, employment_start, employment_end, head_of_house) VALUES (2, 2, 'Minerva', null, 'McGonegall', '1890-10-04', null, '1950-06-22', null, true);
INSERT INTO hogwarts_db.teacher (id, house_id, first_name, middle_name, last_name, date_of_birth, employment, employment_start, employment_end, head_of_house) VALUES (3, 3, 'Pomona', null, 'Sprout', '1910-05-15', null, '1955-10-05', null, true);
INSERT INTO hogwarts_db.teacher (id, house_id, first_name, middle_name, last_name, date_of_birth, employment, employment_start, employment_end, head_of_house) VALUES (4, 4, 'Filius', null, 'Flitwick', '1958-10-17', null, '1978-11-20', null, true);
INSERT INTO hogwarts_db.teacher (id, house_id, first_name, middle_name, last_name, date_of_birth, employment, employment_start, employment_end, head_of_house) VALUES (5, 1, 'Severus', null, 'Snape', '1960-01-09', null, '1980-12-22', null, true);
INSERT INTO hogwarts_db.teacher (id, house_id, first_name, middle_name, last_name, date_of_birth, employment, employment_start, employment_end, head_of_house) VALUES (6, 1, 'Horace', null, 'Slughorn', '1913-04-28', null, '2000-12-12', null, false);


/* MANGLER COURSES og STUDENTS_COURSES */