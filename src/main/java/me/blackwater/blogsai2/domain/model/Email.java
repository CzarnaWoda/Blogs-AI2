package me.blackwater.blogsai2.domain.model;


import jakarta.persistence.Column;

public record Email(@Column(name = "email", nullable = false, unique = true) String value) {
}
