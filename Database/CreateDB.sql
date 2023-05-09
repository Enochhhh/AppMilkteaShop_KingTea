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
    phone char(11),
    date_of_birth date,
    otp_code char(10),
    otp_requested_time timestamp,
    image_url varchar(300),
    constraint UserPrimaryKey primary key(id)
);

create table order_shop (
	id char(10),
    receiver_name nvarchar(50),
    phone char(11),
    address nvarchar(50),
    total_price double,
    total_cost double,
    date_created timestamp,
    payment_method nvarchar(20),
    state nvarchar(20),
    note nvarchar(150),
    user_id char(10),
    constraint OrderShopPrimaryKey primary key(id)
);

create table cart_line (
	user_id char(10),
    custom_milktea_id char(10),
    total_price_on_line double,
    total_cost_on_line double,
    quantity int,
    constraint CartLinePrimaryKey primary key(user_id, custom_milktea_id)
);

create table contact (
	id char(10),
    title nvarchar(50),
    message nvarchar(300),
    email nvarchar(50),
    phone char(11),
    sender_name nvarchar(50),
    user_id char(10),
    constraint ContactPrimaryKey primary key(id)
);

create table milk_tea (
	id char(10),
    name nvarchar(50),
    quantity int,
    price double,
    cost double,
    image_url char(255),
    category_id char(10),
    constraint MilkteaPrimaryKey primary key(id)
);

create table category (
	id char(10),
    name nvarchar(50),
    description nvarchar(1000),
    constraint CategoryPrimaryKey primary key(id)
);

create table custom_milktea (
	id char(10),
    price double,
    cost double,
    size char(2),
    sugar_amount char(5),
    ice_amount char(5),
    milktea_id char(10),
    constraint CustomMilkteaPrimaryKey primary key(id)
);

create table order_line(
	order_id char(10),
    custom_milktea_id char(10),
    total_price_on_line double,
    total_cost_on_line double,
    quantity int,
    constraint OrderLinePrimaryKey primary key(order_id, custom_milktea_id)
);

create table topping (
	id char(10),
    name nvarchar(50),
    price double,
    cost double,
    constraint ToppingPrimaryKey primary key(id)
);

create table add_on_milktea(
	custom_milktea_id char(10),
    topping_id char(10),
    constraint AddOnMilkteaPrimaryKey primary key(custom_milktea_id, topping_id)
);

alter table order_shop add foreign key(user_id) references User(id);
alter table contact add foreign key(user_id) references User(id);
alter table cart_line add foreign key(user_id) references User(id);
alter table cart_line add foreign key(custom_milktea_id) references custom_milktea(id);
alter table milk_tea add foreign key(category_id) references category(id);
alter table custom_milktea add foreign key(milktea_id) references milk_tea(id);
alter table add_on_milktea add foreign key(custom_milktea_id) references custom_milktea(id);
alter table add_on_milktea add foreign key(topping_id) references topping(id);
alter table order_line add foreign key(order_id) references order_shop(id);
alter table order_line add foreign key(custom_milktea_id) references custom_milktea(id);