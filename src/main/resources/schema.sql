-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     phone_number VARCHAR(15) NOT NULL,
                                     password VARCHAR(255) NOT NULL,
                                     first_name VARCHAR(100) NOT NULL,
                                     role VARCHAR(50),
                                     email VARCHAR(100) UNIQUE NOT NULL
);

-- Создание таблицы для хранения состояний машин
CREATE TABLE IF NOT EXISTS users_car_states (
                                                user_id BIGINT NOT NULL,
                                                car_state VARCHAR(255) NOT NULL,
                                                PRIMARY KEY (user_id, car_state),
                                                FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Создание таблицы парковочных мест
CREATE TABLE IF NOT EXISTS parking_spots (
                                             id SERIAL PRIMARY KEY,
                                             number INTEGER NOT NULL,
                                             zone VARCHAR(50) NOT NULL
);
