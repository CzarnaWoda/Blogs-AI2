package me.blackwater.blogsai2.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userName;

    private String password;

    private boolean enabled;

    private boolean blocked;

    @Embedded
    private Phone phone;

    @Embedded
    private Email email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column
    private Set<UserRole> roles = new HashSet<>();


    public User(String userName, String password, boolean enabled, boolean blocked, Phone phone, Email email) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.blocked = blocked;
        this.phone = phone;
        this.email = email;

        addRole("USER");
    }

    public User(String userName, String password, boolean enabled, boolean blocked, Phone phone, Email email, boolean admin, boolean moderator, boolean helper) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.blocked = blocked;
        this.phone = phone;
        this.email = email;

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
}
