DROP TABLE IF EXISTS students_courses;

DROP TABLE IF EXISTS course;

DROP TABLE IF EXISTS student;

CREATE TABLE student
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(20) NOT NULL,
    surname VARCHAR(25) NOT NULL
);

CREATE TABLE course
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    title   VARCHAR(25) UNIQUE NOT NULL,
    details VARCHAR(150)        NOT NULL
);

CREATE TABLE students_courses
(
    student_id INT NOT NULL,
    course_id  INT NOT NULL,
    PRIMARY KEY (student_id, course_id)
);


INSERT INTO student(name, surname)
VALUES ('John', 'Doe'),
       ('Alice', 'Brown'),
       ('John', 'Smith'),
       ('Ali', 'Kaangil'),
       ('Veli', 'Samyeli'),
       ('Allie', 'Curd'),
       ('Rayees', 'Kumar'),
       ('Saurabh', 'Kumar'),
       ('Raj', 'Kumar');


INSERT INTO course(title, details)
VALUES ('Math', 'Algebra 1, Algebra 2, Calculus'),
       ('Physics', 'Kinematics, Newton Law, Momentum'),
       ('Chemistry', 'Changes of state, Density, The Water Molecule and Dissolving'),
       ('Literature', 'New Languages and Litritures'),
       ('History', 'Some Historical Changes, World History'),
       ('Spanish', 'Grammar, Punctuations'),
       ('Biology', 'Human, Plants, Animals, Cell');


INSERT INTO students_courses(student_id, course_id)
VALUES (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (3, 2),
       (3, 4),
       (4, 1),
       (4, 3),
       (4, 5),
       (5, 2),
       (6, 1),
       (6, 5),
       (6, 6);


