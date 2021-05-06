create table categories (id bigserial primary key, title varchar(255));
insert into categories (title) values ('Food');

create table products (id bigserial primary key, title varchar(255), cost int, category_id int);
insert into products (title, cost, category_id) values
('Bread', 25, 1),
('Milk', 80, 1),
('Cheese', 325, 1),
('Cheese3', 725, 1),
('Cheese5', 905, 1),
('Cheese6', 305, 1),
('Cheese6', 305, 1),
('Cheese6', 305, 1),
('Cheese6', 305, 1),
('Cheese6', 305, 1),
('Cheese6', 305, 1),
('Cheese6', 305, 1),
('Cheese6', 305, 1),
('Cheese6', 305, 1),
('Cheese7', 125, 1);