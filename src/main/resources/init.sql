CREATE TABLE IF NOT EXISTS authors
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS books
(
    id SERIAL PRIMARY KEY,
    author_id INT NOT NULL,
    title VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_authors FOREIGN KEY (author_id) REFERENCES authors (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );

INSERT INTO authors (name)
VALUES ('А. С. Пушкин'),
       ('И. С Тургенев'),
       ('А. П. Чехов'),
       ('Л. Н. Толстой'),
       ('И. А. Бунин');

INSERT INTO books(author_id, title, quantity)
VALUES (1, 'Сказка о царе Салтане',3 ),
       (1, 'Дубровский',3 ),
       (1, 'Капитанская дочка',3 ),
       (1, 'Сказка о рыбаке и рыбке',3 ),
       (1, 'Евгений Онегин',3 );

INSERT INTO books(author_id, title, quantity)
VALUES (2, 'Отцы и дети',5),
       (2, 'Дворянское гнездо',2),
       (2, 'Записки охотника',4),
       (2, 'Рудин',1),
       (2, 'Первая любовь',1);

INSERT INTO books(author_id, title, quantity)
VALUES (3, 'Вишневый сад',5),
       (3, 'Мальчики',2),
       (3, 'Каштанка',4),
       (3, 'Ванька',1),
       (3, 'Крыжовник',1);

INSERT INTO books(author_id, title, quantity)
VALUES (4, 'Анна Каренина',5),
       (4, 'Война и мир',2),
       (4, 'Детство',4),
       (4, 'Отец Сергий',1),
       (4, 'Отрочество',1);

INSERT INTO books(author_id, title, quantity)
VALUES (5, 'Тёмные аллеи',5),
       (5, 'Чистый понедельник',2),
       (5, 'Лёгкое дыхание',4),
       (5, 'Солнечный удар',1),
       (5, 'Антоновские яблоки',1);