package me.blackwater.blogsai2.domain.repository;

import me.blackwater.blogsai2.domain.model.Email;
import me.blackwater.blogsai2.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {


    Optional<User> findByEmail(Email email);

    Optional<User> findByUserName(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findById(Long id);

    User save(User user);

    Page<User> findAll(Pageable pageable);

    Page<User> findAllByEmail(String email, Pageable pageable);

    Page<User> findAllByUserRole(String userRole, Pageable pageable);

    Page<User> findAllByUserRoleAndEmail(String userRole, String email, Pageable pageable);


}
