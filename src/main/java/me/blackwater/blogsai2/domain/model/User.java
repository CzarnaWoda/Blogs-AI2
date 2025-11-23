package me.blackwater.blogsai2.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "user")
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userName;

    private String password;

    private boolean enabled;

    private boolean blocked;

    private Instant createdAt;

    private Instant updatedAt;

    @Embedded
    private Phone phone;

    @Embedded
    private Email email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column
    private final Set<UserRole> roles = new HashSet<>();


    public User(String userName, String password, boolean enabled, boolean blocked, Phone phone, Email email) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.blocked = blocked;
        this.phone = phone;
        this.email = email;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();

        addRole("USER");
    }

    public User(String userName, String password, boolean enabled, boolean blocked, Phone phone, Email email, boolean admin, boolean moderator, boolean helper) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.blocked = blocked;
        this.phone = phone;
        this.email = email;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();

        if(admin){
            addRole("ADMIN");
        }
        if(moderator){
            addRole("MODERATOR");
        }
        if(helper){
            addRole("HELPER");
        }
        addRole("USER");
    }


    public void addRole(String role){
        this.roles.add(new UserRole(role));
    }

    public void update(String countryCode, String phoneNumber, String userName){
        this.phone = new Phone(phoneNumber, countryCode);
        this.userName = userName;

        this.updatedAt = Instant.now();
    }

    public void changePassword(String newPassword){
        this.password = newPassword;
        this.updatedAt = Instant.now();
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getValue())).collect(Collectors.toSet());
    }
}
