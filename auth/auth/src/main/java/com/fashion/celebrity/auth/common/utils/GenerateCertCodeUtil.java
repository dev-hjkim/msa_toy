package com.fashion.celebrity.auth.common.utils;

import java.util.Random;

public class GenerateCertCodeUtil {

    private static final String RANDOM_SAMPLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklnmopqrstuvwxyz0123456789";

    public static String generate(int length) {
        StringBuilder certCode = new StringBuilder();

        for (int i=0; i<length; i++) {
            Random random = new Random();
            int randomWithNextInt = random.nextInt(RANDOM_SAMPLE.length());
            certCode.append(RANDOM_SAMPLE.charAt(randomWithNextInt));
        }
        return certCode.toString();
    }
}
