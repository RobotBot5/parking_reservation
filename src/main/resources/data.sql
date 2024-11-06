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
INSERT INTO parking_spots (number, zone, is_occupied)
VALUES
    (1, 'A', FALSE),
    (2, 'A', TRUE),
    (3, 'B', FALSE),
    (4, 'B', TRUE),
    (5, 'C', FALSE),
    (6, 'C', TRUE),
    (7, 'A', FALSE),
    (8, 'A', TRUE),
    (9, 'B', FALSE),
    (10, 'C', TRUE);
