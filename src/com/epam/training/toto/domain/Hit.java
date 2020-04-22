package com.epam.training.toto.domain;

public class Hit {

    private final int hitCount;
    private final int numberOfWagers;
    private final int prize;

    public Hit(int hitCount, int numberOfWager, int prize) {
        this.hitCount = hitCount;
        this.numberOfWagers = numberOfWager;
        this.prize = prize;
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getNumberOfWagers() {
        return numberOfWagers;
    }

    public int getPrize() {
        return prize;
    }
}
