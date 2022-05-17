package org.example.diplom.util;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

public class GenerateToken {

    public static String generateToken(Integer id) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String buf = id + "." + timeStamp;
        return Base64.getEncoder().encodeToString(buf.getBytes());
    }
}
