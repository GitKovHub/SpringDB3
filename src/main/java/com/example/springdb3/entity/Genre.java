package com.example.springdb3.entity;


import java.util.Random;

public enum Genre {
    ACTION,
    ADVENTURE,
    COMEDY,
    DRAMA,
    HORROR,
    ROMANCE,
    SCI_FI,
    THRILLER;

    private static final Random PRNG = new Random();

    public static Genre randomGenre()  {
        Genre[] directions = values();
        return directions[PRNG.nextInt(directions.length)];
    }

}