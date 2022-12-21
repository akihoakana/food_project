drop  database crm_app;
create  database crm_app;

USE crm_app;
CREATE TABLE IF NOT EXISTS roles (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
	username VARCHAR(100) ,
    password VARCHAR(100) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    firstname VARCHAR(100) ,
    lastname VARCHAR(100) ,
    phone VARCHAR(15) ,
    country VARCHAR(100) ,
    avatar VARCHAR(100),
    role_id INT default 1,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS status (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS jobs (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date DATE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tasks (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date DATE,
    user_id INT NOT NULL,
    job_id INT NOT NULL,
    status_id INT NOT NULL,
    PRIMARY KEY (id)
);

-- CREATE TABLE IF NOT EXISTS users_detail (
--     user_id INT NOT NULL,
--     age int,
--     cmnd varchar(12),
--     
--     PRIMARY KEY (user_id),
--     foreign key(user_id) references users(id)
-- );


ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles (id)  ON DELETE CASCADE;
ALTER TABLE tasks ADD FOREIGN KEY (user_id) REFERENCES users (id)  ON DELETE CASCADE;
ALTER TABLE tasks ADD FOREIGN KEY (job_id) REFERENCES jobs (id)  ON DELETE CASCADE;
ALTER TABLE tasks ADD FOREIGN KEY (status_id) REFERENCES status (id)  ON DELETE CASCADE;

INSERT INTO roles( name, description ) VALUES ("ROLE_ADMIN", "Quản trị viên");
INSERT INTO roles( name, description ) VALUES ("ROLE_MANAGER", "Quản lý");
INSERT INTO roles( name, description ) VALUES ("ROLE_DEVELOPER", "Lập trình viên");
INSERT INTO roles( name, description ) VALUES ("ROLE_MEMBER", "Thành viên");
INSERT INTO roles( name, description ) VALUES ("ROLE_SUPPORTER", "Nhân viên hỗ trợ");

INSERT INTO jobs( name, start_date,end_date ) VALUES ("Dự án ABC",'2019-05-22','2019-09-30');
INSERT INTO jobs( name, start_date,end_date ) VALUES ("Dự án DEF",'2019-10-02','2019-12-10');


INSERT INTO status( name ) VALUES ("Chưa bắt đầu");
INSERT INTO status( name ) VALUES ("Đang thực hiện");
INSERT INTO status( name ) VALUES ("Hoàn thành");


INSERT INTO users(email,password,fullname,phone,country ) 
VALUES ("nguyenvana@gmail.com","123456","Nguyễn Văn A","011111111","Nguyễn");

INSERT INTO users(email,password,fullname,firstname,lastname,username,phone,country,avatar,role_id ) 
VALUES ("nguyenvana@gmail.com","123456","Nguyễn Văn A","A","Nguyễn","anv","","","",1);

INSERT INTO users(email,password,fullname,firstname,lastname,username,phone,country,avatar,role_id ) 
VALUES ("nguyenvanb@gmail.com","456","Nguyễn Văn B","B","Nguyễn","bnv","","","",2);

INSERT INTO users(email,password,fullname,firstname,lastname,username,phone,country,avatar,role_id ) 
VALUES ("nguyenvanc@gmail.com","789","Nguyễn Văn C","C","Nguyễn","cnv","","","",3);

INSERT INTO users(email,password,fullname,firstname,lastname,username,phone,country,avatar,role_id ) 
VALUES ("nguyenvand@gmail.com","456789","Nguyễn Văn D","D","Nguyễn","dnv","","","",4);

INSERT INTO tasks( name, start_date,end_date,user_id,job_id,status_id ) VALUES ("Phân tích dự án",'2019-05-22','2019-06-21',2,1,1);
INSERT INTO tasks( name, start_date,end_date,user_id,job_id,status_id ) VALUES ("Thiết kế hệ thống",'2019-06-22','2019-07-21',3,1,1);
INSERT INTO tasks( name, start_date,end_date,user_id,job_id,status_id ) VALUES ("Thi công hệ thống",'2019-07-22','2019-08-21',4,2,1);
INSERT INTO tasks( name, start_date,end_date ,user_id,job_id,status_id ) VALUES ("Kiểm định dự án",'2019-08-22','2019-09-21',2,2,1);
INSERT INTO tasks( name, start_date,end_date ,user_id,job_id,status_id ) VALUES ("Kiểm định dự án 22",'2019-08-22','2019-09-21',3,2,1);
INSERT INTO tasks( name, start_date,end_date ,user_id,job_id,status_id ) VALUES ("Kiểm định dự án 22",'2019-08-22','2019-09-21',4,2,1);
INSERT INTO tasks( name, start_date,end_date ,user_id,job_id,status_id ) VALUES ("Kiểm định dự án 22",'2019-08-22','2019-09-21',4,2,1);
INSERT INTO tasks( name, start_date,end_date,user_id,job_id,status_id ) VALUES ("Phân tích dự án 222",'2019-05-22','2019-06-21',2,1,2);
INSERT INTO tasks( name, start_date,end_date,user_id,job_id,status_id ) VALUES ("Thiết kế hệ thống 222",'2019-06-22','2019-07-21',3,1,2);
INSERT INTO tasks( name, start_date,end_date,user_id,job_id,status_id ) VALUES ("Phân tích dự án 333",'2019-05-22','2019-06-21',2,1,3);
INSERT INTO tasks( name, start_date,end_date,user_id,job_id,status_id ) VALUES ("Thiết kế hệ thống 333",'2019-06-22','2019-07-21',3,1,3);

UPDATE users
SET email = 'Alfrednguyenvand@gmail.com', password= '456789',fullname='Alfrednguyenvand',firstname= 'Alfred',lastname= 'nguyenvand',username= 'Alfrednguyenvand',role_id=1
WHERE users.id = 2;

UPDATE tasks
SET name = 'Alfred Schmidt', start_date= '2019-08-22',end_date='2019-09-21',user_id= 1,job_id= 2,status_id= 2
WHERE tasks.id = 2;

UPDATE tasks
SET name = 'Alfred Schmi5555dt', start_date= '2019-08-22',end_date='2019-09-21',job_id= 2,status_id= 2
WHERE user_id = 1 and tasks.id = 1;

select *
from status;
select *
from jobs;
select *
from tasks;
select users.*
from users
where users.email="Nguyenvana@gmail.com" and users.password="123456";

SELECT id,firstname, lastname,username,role_id
FROM users
where u.email="Nguyenvana@gmail.com" and u.password="123456";

select * from roles;

delete from roles r where r.id=1;

SELECT users.id,users.firstname,users.lastname,users.username,roles.name as rolesname
FROM users
LEFT JOIN roles
ON users.role_id = roles.id;

SELECT tasks.id,tasks.name,jobs.name as jobsname,users.fullname,tasks.start_date,tasks.end_date,status.name as statusname
FROM tasks
LEFT JOIN jobs
ON tasks.job_id = jobs.id
LEFT JOIN users
ON tasks.user_id = users.id
LEFT JOIN status
ON tasks.status_id = status.id;

SELECT tasks.name,jobs.name as jobsname,users.fullname,users.email,tasks.start_date,tasks.end_date,status.name as statusname
FROM tasks 
LEFT JOIN jobs
ON tasks.job_id = jobs.id
LEFT JOIN status
ON tasks.status_id = status.id
left JOIN users
ON tasks.user_id = users.id 
where users.id= 1;

SELECT tasks.name,tasks.start_date,tasks.end_date
FROM tasks 
left JOIN status
ON tasks.status_id = status.id
left JOIN users
ON tasks.user_id = users.id 
where users.id=5 and status.id =1;    

SELECT users.fullname,users.email 
FROM users 
where users.id=5;
                    
SELECT tasks.name,tasks.start_date,tasks.end_date,status.id
FROM status 
left JOIN tasks
ON tasks.status_id = status.id
left JOIN users
ON tasks.user_id = users.id where users.id=4 ;
-- groupwork-detail
SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
set session sql_mode='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
SELECT  tasks.id,tasks.name as tasksname, jobs.name as jobsname,users.fullname,status.name as statusname ,count( status.name)
FROM tasks 
left JOIN status
ON tasks.status_id = status.id
left JOIN users
ON tasks.user_id = users.id 
left JOIN jobs
ON tasks.job_id = jobs.id
where jobs.id= 1 
group by status.name ;

SELECT status.name,  count( status.name) as count
FROM tasks 
right JOIN status
ON tasks.status_id = status.id
left JOIN users
ON tasks.user_id = users.id 
where users.id= 4
group by status.name ;
SELECT * FROM crm_app.status;

SELECT status.name,  count( status.name) as count
FROM tasks 
right JOIN status
ON tasks.status_id = status.id
left JOIN users
ON tasks.user_id = users.id 
where users.id= 4
group by status.name ;

-- ///groupwork-detail
SELECT  distinct users.fullname
FROM tasks 
left JOIN jobs
ON tasks.job_id = jobs.id
left JOIN users
ON tasks.user_id = users.id 
where jobs.id= 2 ;

SELECT users.fullname,tasks.name,tasks.start_date,tasks.end_date,status.name as statusname
FROM tasks 
left JOIN jobs
ON tasks.job_id = jobs.id
left JOIN status
ON tasks.status_id = status.id
left JOIN users
ON tasks.user_id = users.id 
where jobs.id= 2 ;

SELECT  count( status.name) as count
FROM tasks 
left JOIN jobs
ON tasks.job_id = jobs.id
left JOIN status
ON tasks.status_id = status.id
where jobs.id= 2 and status.id=1;

-- ///////

SELECT count( status.name) as count 
FROM tasks 
left JOIN status 
ON tasks.status_id = status.id
left JOIN users
ON tasks.user_id = users.id
 where users.id= 1 and status.id =3;

SELECT fullname
FROM users; 
select id,firstname, lastname,username,role_id from users;

select * from roles;

UPDATE roles
SET name = 'Alfred Schmidt', description= 'Frankfurt'
WHERE roles.id = 12;

UPDATE status
SET name = 'Hoàn thành'
WHERE status.id = 3;
