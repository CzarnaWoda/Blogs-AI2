package me.blackwater.blogsai2.domain.model;


import jakarta.persistence.Column;
import me.blackwater.blogsai2.infrastructure.util.ValidationUtil;
import me.blackwater.blogsai2.domain.exception.InvalidEmailException;

public record Email(@Column(name = "email", nullable = false, unique = true) String value) {

    public Email {
        if (ValidationUtil.validateEmail(value)) {
            throw new InvalidEmailException("Email is not valid");
        }

    }
}
