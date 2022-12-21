drop database food_delivery;

create database food_delivery;
use food_delivery;

create table user(
	id int auto_increment,
    email varchar(255),
    password varchar(255),
    fullname varchar(100),
    token varchar(100),
    type_token varchar(10),
    phone_number varchar(12),
    verify_code varchar(12),
    verify_code_expired timestamp,
    is_active boolean default false,
    
    primary key(id)
);

create table user_detail(
	id_user int,
    address varchar(255),
    mobile_phone varchar(12),
    state varchar(255),
    city varchar(255),
    street varchar(255),
    
    primary key(id_user),
    foreign key(id_user) references user(id)
);

create table category(
	id int auto_increment,
    image text,
    name varchar(50),
    
    primary key(id)
);

create table restaurant(
	id int auto_increment,
    image text,
    name varchar(100),
    mainfood varchar(100),
    
    primary key(id)
);

create table restaurant_review(
	id int auto_increment,
    id_restaurant int,
    content text,
    rate float,
    
    primary key(id),
    foreign key(id_restaurant) references restaurant(id)
);

create table food(
	id int auto_increment,
    name varchar(255),
    image text,
    price int,
    id_category int,
    id_restaurant int,
    
    primary key(id),
    foreign key(id_category) references category(id),
    foreign key(id_restaurant) references restaurant(id)
);

create table food_detail(
	id_food int ,
    description text,
    create_date timestamp,
    rating float,
    
    primary key(id_food),
    foreign key(id_food) references food(id)
);

create table food_review(
	id int auto_increment,
    id_food int,
    id_user int,
    content text,
    create_date timestamp default now(),
    rate float,
    
    primary key(id),
    foreign key(id_food) references food(id),
    foreign key(id_user) references user(id)
);

create table food_addon(
	id int auto_increment,
    name varchar(100),
    image text,
    price int,
    id_food int,
    
    primary key(id),
    foreign key(id_food) references food(id)
);

create table material(
	id int auto_increment,
    name varchar(100),
    
    primary key(id)
);

create table food_material(
	id_food int,
    id_material int,
    
    primary key(id_food,id_material),
    foreign key(id_food) references food(id),
    foreign key(id_material) references material(id)
);

create table t_order(
	id int auto_increment,
	id_user int,
    estimate_ship timestamp,
    deliver_address text,
    
    primary key(id),
    foreign key(id_user) references user(id)
);

create table status(
	id int auto_increment,
	name varchar(50),
    
    primary key(id)
);

create table order_status(
	id_order int,
    id_status int,
    
    primary key(id_order,id_status),
    foreign key(id_order) references t_order(id),
    foreign key(id_status) references status(id)
);

create table food_order(
	id_order int,
    id_food int,
    price float,
    quality int,
    
    primary key(id_order,id_food),
    foreign key(id_order) references t_order(id),
    foreign key(id_food) references food(id)
);
create table cart(
	id int auto_increment,
    product varchar(255),
    id_food int,
    id_order int,
    price float,
    quality int,
    
    primary key(id),
    foreign key(id_order) references t_order(id),
    foreign key(id_food) references food(id)
);
insert into user (email,password,fullname) values 
('abc@gmail.com','$2a$10$2cFa4CPNNWPQoLaQ7uUw3OwfBGUjRNEqsgg1MFutXNbZsR68JOtLK','aaaaaaaaaa')
,('bbb@gmail.com','$2a$10$2cFa4CPNNWPQoLaQ7uUw3OwfBGUjRNEqsgg1MFutXNbZsR68JOtLK','bbbbbbbbb')
,('ddd@gmail.com','$2a$10$2cFa4CPNNWPQoLaQ7uUw3OwfBGUjRNEqsgg1MFutXNbZsR68JOtLK','cccccccc');
insert into category values (1,'hinha','cate1'),(2,'hinhb','cate2'),(3,'hinhc','cate3');
insert into restaurant values (1,'restauranthinha','restaurant1','mainfood1'),(2,'restauranthinhb','restaurant2','mainfood2'),(3,'restauranthinhc','restaurant3','mainfood3');
insert into material values (1,'material1'),(2,'material2'),(3,'material3'),(4,'material4');
insert into status values (1,'status1'),(2,'status2'),(3,'status3'),(4,'status4');
insert into food values (1,'food1','hinha',10,1,2),(2,'food2','hinh2',12,2,3),(3,'food3','hinh3',33,3,1),(4,'food4','hinh4',14,1,1);
insert into restaurant_review values (1,1,'content1',4),(2,1,'content2',2)
,(3,2,'content3',5),(4,2,'content4',2),(5,3,'content5',1),(6,3,'content6',2);
-- -------------
insert into t_order values (1,2,'1000-01-01 00:00:00','deliver_address1'),(2,1,'1000-01-01 00:30:00','deliver_address2'),(3,3,'1000-01-01 00:20:00','deliver_address3');
insert into cart values (1,'product1',2,1,12,2),(2,'product2',1,3,14,3),(3,'product3',3,2,12,1);
SELECT * FROM food_delivery.user;
SELECT * FROM food_delivery.food;
SELECT * FROM food_delivery.restaurant;
SELECT * FROM food_delivery.category;

