package me.blackwater.blogsai2.infrastructure.repository;

import me.blackwater.blogsai2.domain.model.Email;
import me.blackwater.blogsai2.domain.model.Phone;
import me.blackwater.blogsai2.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserJpaRepository extends JpaRepository<User, Long> {


    Optional<User> findByUserName(String username);

    Optional<User> findByEmail(Email email);

    Optional<User> findByPhone(Phone phone);

    @Query("SELECT u FROM user u WHERE LOWER(u.email.value) LIKE LOWER(CONCAT('%', :email, '%'))")
    Page<User> findAllByEmailContainingIgnoreCase(@Param("email") String email, Pageable pageable);

    @Query("SELECT u from user u JOIN u.roles role where role.value = :role")
    Page<User> findAllByUserRole(@Param("role") String userRole, Pageable pageable);

    @Query("SELECT u from user  u JOIN u.roles role where role.value = :role AND LOWER(u.email.value) like LOWER(CONCAT('%', :email, '%'))")
    Page<User> findAllByEmailAndUserRole(@Param("email") String email, @Param("role") String userRole, Pageable pageable);
}
