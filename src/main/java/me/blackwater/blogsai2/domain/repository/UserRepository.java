package me.blackwater.blogsai2.domain.repository;

import me.blackwater.blogsai2.domain.model.Email;
import me.blackwater.blogsai2.domain.model.User;

import java.util.Optional;

public interface UserRepository {


    Optional<User> findByEmail(Email email);

    Optional<User> findByUserName(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findById(Long id);

    User save(User user);


}
