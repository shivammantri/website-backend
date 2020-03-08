package com.northeast.helper;

import java.util.Random;
import java.util.UUID;

public class IdGenerator {

    private final static Random rand = new Random();

    private static String getUUIDGeneratedValue() {
        long randomNumber = (long) (rand.nextInt(999) + 1000);
        String uuid = UUID.randomUUID().toString();
        return uuid + randomNumber;
    }

    public static String generateUserId(){
        return "NEY"+getUUIDGeneratedValue();
    }

    public static String generateSessionId() {
        return "SE" + getUUIDGeneratedValue();
    }
}
