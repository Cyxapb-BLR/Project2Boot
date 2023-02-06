CREATE
    database project1_db;

drop table Book;
drop table Person;
create table Person
(
    id            int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          varchar(100) UNIQUE NOT NULL,
    year_of_birth int CHECK ( year_of_birth > 1900 )
);
create table Book
(
    id                 int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title              varchar(100) NOT NULL,
    author             varchar(60)  NOT NULL,
    person_id          int          REFERENCES Person (id) ON DELETE SET NULL,
    year_of_publishing int CHECK ( year_of_publishing > 1500 )
);

SELECT *
FROM book;

INSERT INTO Book(title, author, year_of_publishing)
VALUES ('title3', 'author3', '2000');
INSERT INTO Book(title, author, year_of_publishing, person_id)
VALUES ('title4', 'author4', '2000', 2);

SELECT Person.*
FROM Person
         JOIN Book B on Person.id = B.person_id
WHERE B.id = 2;

ALTER TABLE Book
    ADD COLUMN taken_at TIMESTAMP;

INSERT INTO Book(title, author, person_id, year_of_publishing, taken_at)
values ('title5','author5',2,1905,'2022-12-31 12:14:10');