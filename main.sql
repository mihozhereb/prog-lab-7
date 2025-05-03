DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS MusicBands CASCADE;

DROP TYPE IF EXISTS MusicGenre;
DROP TYPE IF EXISTS Color;

CREATE TYPE MusicGenre AS ENUM ('PSYCHEDELIC_ROCK', 'BLUES', 'MATH_ROCK', 'POST_ROCK', 'POST_PUNK');
CREATE TYPE Color AS ENUM ('BLUE', 'ORANGE', 'WHITE');

CREATE TABLE Users(
    UserId SERIAL PRIMARY KEY NOT NULL,
    Login VARCHAR(64) UNIQUE NOT NULL,
    Password VARCHAR(64) NOT NULL
);

CREATE TABLE MusicBands(
    Id SERIAL PRIMARY KEY NOT NULL,
    Name VARCHAR(64) NOT NULL,
    X DECIMAL NOT NULL CHECK ( X > -823 ),
    Y FLOAT NOT NULL CHECK ( Y <= 752 ),
    CreationDate TIMESTAMP DEFAULT NOW(),
    NumberOfParticipants INT NOT NULL CHECK ( NumberOfParticipants > 0 ),
    Genre MusicGenre,
    PersonName VARCHAR(64) NOT NULL,
    PersonBirthday DATE NOT NULL,
    PersonHeight DECIMAL CHECK ( PersonHeight > 0 ),
    PersonWeight INT NOT NULL CHECK ( PersonWeight > 0 ),
    PersonHairColor Color,
    OwnerId INT NOT NULL REFERENCES Users(UserId)
);