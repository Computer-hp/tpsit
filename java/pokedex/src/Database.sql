CREATE DATABASE pokedex;

USE pokedex;

CREATE TABLE pokemons (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type1 VARCHAR(20) NOT NULL,
    type2 VARCHAR(20)
);
