package me.blackwater.blogsai2.domain.model;

import jakarta.persistence.Column;
import me.blackwater.blogsai2.infrastructure.util.ValidationUtil;
import me.blackwater.blogsai2.domain.exception.InvalidCountryCodeException;
import me.blackwater.blogsai2.domain.exception.InvalidPhoneNumberException;

public record Phone(@Column(name = "phone_number", nullable = false) String value,
                    @Column(name = "phone_country_code", nullable = false) String countryCode) {

    public Phone {
        if (ValidationUtil.isValidCountryCode(countryCode)) {
            throw new InvalidCountryCodeException("Invalid country code: " + countryCode);
        }
        if (ValidationUtil.isValidPhoneNumber(value)) {
            throw new InvalidPhoneNumberException("Invalid phone number: " + value);
        }

    }


}
