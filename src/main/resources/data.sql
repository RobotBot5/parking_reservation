-- Вставка данных пользователей
INSERT INTO users (phone_number, password, first_name, role, email)
VALUES
    ('admin', '$2a$12$U5jH00ogm2stdbIcuJ3.3O0dSnpieQLpAJjP/j8lYNZlSpgwSZRuC', 'Admin', 'ROLE_ADMIN', 'admin'),
    ('+79990000001', '$2a$12$U5jH00ogm2stdbIcuJ3.3O0dSnpieQLpAJjP/j8lYNZlSpgwSZRuC', 'John', 'ROLE_USER', 'john.doe@example.com');

-- Вставка состояний машин (для пользователей)
INSERT INTO users_car_states (user_id, car_state)
VALUES
    (1, 'В261ЕН777'),
    (1, 'А111АА123');

-- Вставка парковочных мест
INSERT INTO parking_spots (number, zone)
VALUES
    (1, 'A'),
    (2, 'A'),
    (3, 'B'),
    (4, 'B'),
    (5, 'C'),
    (6, 'C'),
    (7, 'A'),
    (8, 'A'),
    (9, 'B'),
    (10, 'C');
