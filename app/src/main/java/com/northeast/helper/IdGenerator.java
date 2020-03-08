package com.northeast.helper;

import java.util.Random;
import java.util.UUID;

public class IdGenerator {

    private final static Random rand = new Random();

    private static String getUUIDGeneratedValue() {
        long currentTime = System.nanoTime();
        long randomNumber = (long) (rand.nextInt(999) + 1000);
        String uuid = new UUID(currentTime, currentTime).toString();
        return uuid.substring(uuid.length() - 6) + randomNumber;
    }

    public static String generateUserId(){
        return "NEY"+getUUIDGeneratedValue();
    }

    public static String generateSessionId() {
        long randomNumber = (long) (rand.nextInt(999) + 1000);
        String uuid = UUID.randomUUID().toString();
        String randomUUID =  uuid + randomNumber;
        return "SE" + randomUUID;
    }
}
