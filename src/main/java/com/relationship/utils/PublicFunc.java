package com.relationship.utils;

import java.util.regex.Pattern;

public class PublicFunc {

    public static boolean matchPhone(String number){
        if (number.isEmpty()){
            return false;
        }
        //符合返回true
        if (Pattern.matches("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9]|17[0|6|7|8])\\d{8}$", number)) {
            return true;
        }
        return false;
    }

    public static long createUniqueId(long lastUniqueId){
        long newUniqueId = lastUniqueId;
        while (true) {
            newUniqueId += 1;
            String strUniqueId = String.valueOf(newUniqueId);
            if (Pattern.matches("\\d*(\\d)\\1{4}", strUniqueId)) {
                continue;
            }
            if (Pattern.matches("(0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){3,}", strUniqueId)) {
                continue;
            }
            break;
        }
        return newUniqueId;
    }
}
