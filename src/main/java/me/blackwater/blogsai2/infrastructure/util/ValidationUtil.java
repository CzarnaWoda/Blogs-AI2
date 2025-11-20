package me.blackwater.blogsai2.infrastructure.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static final String EMAIL_REGEX =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    private static final int MAX_EMAIL_LENGTH = 126;

    public static boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty() || email.length() > MAX_EMAIL_LENGTH) {
            return false;
        }
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isValidCountryCode(String code) {
        final String ePattern = "^\\+\\d{1,3}$";
        final Pattern pattern = Pattern.compile(ePattern);

        Matcher matcher = pattern.matcher(code);

        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String number) {
        final String ePattern = "^\\d{9}$";
        final Pattern pattern = Pattern.compile(ePattern);

        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }
}
