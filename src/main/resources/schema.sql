DROP TABLE IF EXISTS time_table_tb CASCADE ;

DROP TABLE IF EXISTS group_student_tb CASCADE;

DROP TABLE IF EXISTS group_time_table_tb CASCADE;

DROP TABLE IF EXISTS payment_tb CASCADE;

DROP TABLE IF EXISTS group_tb CASCADE;

DROP TABLE IF EXISTS student_tb CASCADE;

DROP TABLE IF EXISTS pay_type_tb CASCADE;

DROP TABLE IF EXISTS day_tb CASCADE;

DROP TABLE IF EXISTS status_tb CASCADE;

DROP TABLE IF EXISTS room_tb CASCADE;

DROP TABLE IF EXISTS course_tb CASCADE;

DROP TABLE IF EXISTS teacher_tb CASCADE;

DROP TABLE IF EXISTS user_tb CASCADE;

CREATE TABLE day_tb (
      id bigserial primary key,
      name varchar(50) unique
);

CREATE TABLE course_tb (
      id bigserial primary key,
      name varchar(50) unique,
      price double precision,
      duration varchar(50)
);

CREATE TABLE teacher_tb (
      id bigserial primary key,
      full_name varchar(50) ,
      phone varchar(20),
      salary double precision
);


CREATE TABLE room_tb (
      id bigserial primary key,
      name varchar(50),
      capacity int
);

CREATE TABLE status_tb (
     id bigserial primary key,
     name varchar(50),
     description varchar
);

CREATE TABLE student_tb (
        id bigserial primary key,
        full_name varchar(50) ,
        phone varchar(20)
);

CREATE TABLE pay_type_tb (
        id bigserial primary key,
        name varchar(50)
);


CREATE TABLE payment_tb (
        id bigserial primary key,
        full_name varchar(50) ,
        pay_type_id int,
        sum double precision,
        description varchar,
        student_id int,
        created_date date,

        CONSTRAINT fk_student_id
            FOREIGN KEY(student_id)
                REFERENCES status_tb(id),
        CONSTRAINT fk_pay_type_id
            FOREIGN KEY(pay_type_id)
                REFERENCES pay_type_tb(id)
);



CREATE TABLE group_tb (
         id bigserial primary key,
         name varchar(50),
         course_id int,
         CONSTRAINT fk_course_id
             FOREIGN KEY(course_id)
                 REFERENCES course_tb(id),
         teacher_id int,
         CONSTRAINT fk_teacher_id
             FOREIGN KEY(teacher_id)
                 REFERENCES teacher_tb(id),
         room_id int,
         CONSTRAINT fk_room_id
             FOREIGN KEY(room_id)
                 REFERENCES room_tb(id),
         start_date date,
         end_date date,
         status_id int,
         CONSTRAINT fk_status_id
             FOREIGN KEY(status_id)
                 REFERENCES status_tb(id)


);

CREATE TABLE group_student_tb (
         id bigserial primary key,
         group_id int,
         CONSTRAINT fk_group_id
             FOREIGN KEY(group_id)
                 REFERENCES group_tb(id),
         student_id int,
         CONSTRAINT fk_student_id
             FOREIGN KEY(student_id)
                 REFERENCES student_tb(id)

);

CREATE TABLE time_table_tb (
         id bigserial primary key,
         day_id int,
         CONSTRAINT fk_day_id
             FOREIGN KEY(day_id)
                 REFERENCES day_tb(id),
         start_time date,
         end_time date
);

CREATE TABLE group_time_table_tb (
           id bigserial primary key,
           time_table_id int,
           CONSTRAINT fk_time_table_id
               FOREIGN KEY(time_table_id)
                   REFERENCES time_table_tb(id),
           group_id int,
           CONSTRAINT fk_group_id
               FOREIGN KEY(group_id)
                   REFERENCES group_tb(id)

);

CREATE TABLE user_tb(

    id bigserial primary key,
    name varchar(25),
    username varchar(25),
    password varchar,
    role varchar(25)

);


