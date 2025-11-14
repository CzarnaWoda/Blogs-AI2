package me.blackwater.blogsai2.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserRole {

    @Column(name = "role")
    private String value;

    public UserRole(String value) {
        this.value = value;
    }


}
