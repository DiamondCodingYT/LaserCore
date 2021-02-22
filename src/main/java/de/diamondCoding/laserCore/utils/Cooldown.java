package de.diamondCoding.laserCore.utils;

import lombok.Getter;

@Getter
public class Cooldown {

    private final long start;
    private final long end;

    public Cooldown(long start, long end) {
        this.start = start;
        this.end = end;
    }

}
