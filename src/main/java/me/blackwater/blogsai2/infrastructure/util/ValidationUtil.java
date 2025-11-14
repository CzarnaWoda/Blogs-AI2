package me.blackwater.blogsai2.api.util;

public class ValidationUtil {
    public static final String EMAIL_REGEX =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String COUNTRY_CODE_REGEX = "^\\+[0-9]{1,3}$";
    public static final String PHONE_REGEX = "^\\+[1-9][0-9]{1,14}$";

    public static boolean validateEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }
    public static boolean isValidCountryCode(String code) {
        return code != null && code.matches(COUNTRY_CODE_REGEX);
    }
    public static boolean isValidPhoneNumber(String number) {
        return number != null && number.matches(PHONE_REGEX);
    }
}
