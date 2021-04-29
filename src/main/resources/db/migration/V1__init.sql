create table products (id bigserial primary key, title varchar(255), cost int);
insert into products (title, cost) values
('Bread', 25),
('Milk', 80),
('Cheese', 325);