drop database if exists MilkteaShop_KingTea;
create database MilkteaShop_KingTea;

use MilkteaShop_KingTea;

drop table if exists user;
drop table if exists order_shop;
drop table if exists cart_line;
drop table if exists contact;
drop table if exists milk_tea;
drop table if exists category;
drop table if exists custom_milktea;
drop table if exists order_line;
drop table if exists topping;
drop table if exists add_on_milktea;

create table user (
	id char(10),
    user_name nvarchar(50) not null unique,
    password nvarchar(150) not null,
    role nvarchar(10) not null,
    created_at timestamp not null,
    full_name nvarchar(50),
    address nvarchar(50),
    email nvarchar(50) not null,
    phone char(11) not null,
    date_of_birth date,
    otp_code char(10),
    otp_requested_time timestamp,
    image_url varchar(300),
    enabled tinyint(1) not null,
    constraint UserPrimaryKey primary key(id)
);

create table order_shop (
	order_id char(10),
    receiver_name nvarchar(50) not null,
    phone char(11) not null,
    address nvarchar(50) not null,
    total_price int,
    total_cost int,
    date_created timestamp not null,
    payment_method nvarchar(20),
    state nvarchar(20) not null,
    note nvarchar(300),
    user_id char(10) not null,
    enabled tinyint(1) not null,
    constraint OrderShopPrimaryKey primary key(order_id)
);

create table cart_line (
	cartline_id int auto_increment,
	user_id char(10),
    custom_milktea_id char(10),
    total_price_on_line double,
    total_cost_on_line double,
    quantity int,
    constraint CartLinePrimaryKey primary key(cartline_id)
);

create table contact (
	contact_id char(10),
    title nvarchar(50),
    message nvarchar(300),
    email nvarchar(50),
    phone char(11),
    sender_name nvarchar(50),
    user_id char(10),
    enabled tinyint(1) not null,
    constraint ContactPrimaryKey primary key(contact_id)
);

create table milk_tea (
	milktea_id char(10),
    name nvarchar(50),
    quantity int,
    price int,
    cost int,
    image_url char(255),
    category_id char(10),
    enabled tinyint(1) not null,
    constraint MilkteaPrimaryKey primary key(milktea_id)
);

create table category (
	category_id char(10),
    name nvarchar(50),
    description nvarchar(1000),
    img_url char(255),
    enabled tinyint(1) not null,
    constraint CategoryPrimaryKey primary key(category_id)
);

create table custom_milktea (
	custom_milktea_id char(10),
    price int,
    cost int,
    size char(2),
    sugar_amount char(5),
    ice_amount char(5),
    milktea_id char(10),
    enabled tinyint(1) not null,
    constraint CustomMilkteaPrimaryKey primary key(custom_milktea_id)
);

create table order_line(
	orderline_id int auto_increment,
	order_id char(10),
    custom_milktea_id char(10),
    total_price_on_line int,
    total_cost_on_line int,
    quantity int,
    constraint OrderLinePrimaryKey primary key(orderline_id)
);

create table topping (
	topping_id char(10),
    name nvarchar(50),
    price int,
    cost int,
    enabled tinyint(1) not null,
    constraint ToppingPrimaryKey primary key(topping_id)
);

create table add_on_milktea(
	custom_milktea_id char(10),
    topping_id char(10),
    constraint AddOnMilkteaPrimaryKey primary key(custom_milktea_id, topping_id)
);

alter table order_shop add foreign key(user_id) references User(id);
alter table contact add foreign key(user_id) references User(id);
alter table cart_line add foreign key(user_id) references User(id);
alter table cart_line add foreign key(custom_milktea_id) references custom_milktea(custom_milktea_id);
alter table milk_tea add foreign key(category_id) references category(category_id);
alter table custom_milktea add foreign key(milktea_id) references milk_tea(milktea_id);
alter table add_on_milktea add foreign key(custom_milktea_id) references custom_milktea(custom_milktea_id);
alter table add_on_milktea add foreign key(topping_id) references topping(topping_id);
alter table order_line add foreign key(order_id) references order_shop(order_id);
alter table order_line add foreign key(custom_milktea_id) references custom_milktea(custom_milktea_id);