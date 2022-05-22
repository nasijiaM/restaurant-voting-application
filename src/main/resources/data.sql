INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT(name)
VALUES ('FirstRestaurant'),
       ('SecondRestaurant');

INSERT INTO MENUITEM(restaurant_id, name, price)
VALUES (1, 'Fish', 250),
       (1, 'Rice', 100),
       (1, 'Tea', 20),
       (2, 'Meat', 200),
       (2, 'Pasta', 100),
       (2, 'Coffee', 40);

INSERT INTO VOTE(restaurant_id, user_id)
VALUES (2, 2);

INSERT INTO VOTE(restaurant_id, user_id, date)
VALUES (1, 1, '2022-05-15'),
       (2, 2, '2022-05-15');