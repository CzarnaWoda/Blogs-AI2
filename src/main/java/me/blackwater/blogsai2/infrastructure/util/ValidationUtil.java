package me.blackwater.blogsai2.infrastructure.util;

public class ValidationUtil {
    public static final String EMAIL_REGEX =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    public static final String COUNTRY_CODE_REGEX = "^\\+[1-9][0-9]{0,2}$";

    public static final String PHONE_REGEX = "^\\+[1-9][0-9]{7,14}$";

    private static final int MAX_EMAIL_LENGTH = 126;

    public static boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty() || email.length() > MAX_EMAIL_LENGTH) {
            return false;
        }
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isValidCountryCode(String code) {
        return code != null && !code.trim().isEmpty() && code.matches(COUNTRY_CODE_REGEX);
    }

    public static boolean isValidPhoneNumber(String number) {
        return number != null && !number.trim().isEmpty() && number.matches(PHONE_REGEX);
    }
}
