package com.qy105.aaa.util;

import java.util.Random;

public class FileNameUtil {
    public static String getNewFileName() {
        long currentTime = System.currentTimeMillis();
        Random random = new Random();
        int randNum = random.nextInt(1000);
        String fileNewName = currentTime + String.valueOf(randNum);
        return fileNewName;
    }
}
