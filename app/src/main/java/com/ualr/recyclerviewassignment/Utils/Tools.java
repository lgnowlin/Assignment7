package com.ualr.recyclerviewassignment.Utils;

import java.util.Random;

public class Tools {
    public static String getEmailFromName(String name) {
        if (name != null && !name.equals("")) {
            String email = name.replaceAll(" ", ".").toLowerCase().concat("@mail.com");
            return email;
        }
        return name;
    }

    public static int getRandomNumberInRange(int min, int max) {
         Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
