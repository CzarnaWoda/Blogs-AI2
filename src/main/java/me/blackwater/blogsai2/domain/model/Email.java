package me.blackwater.blogsai2.domain.data;


import jakarta.persistence.Column;
import lombok.Getter;
import me.blackwater.blogsai2.infrastructure.util.ValidationUtil;
import me.blackwater.blogsai2.domain.exception.InvalidEmailException;

@Getter
public record Email(@Column(name = "email", nullable = false, unique = true) String value) {

    public Email {
        if (ValidationUtil.validateEmail(value)) {
            throw new InvalidEmailException("Email is not valid");
        }

    }
}
