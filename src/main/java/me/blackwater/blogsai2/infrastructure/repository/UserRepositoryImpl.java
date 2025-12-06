package me.blackwater.blogsai2.infrastructure.repository;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.domain.model.Email;
import me.blackwater.blogsai2.domain.model.Phone;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(Email email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userJpaRepository.findByUserName(username);
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userJpaRepository.findByPhone(new Phone(phoneNumber,"+48"));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userJpaRepository.findAll(pageable);
    }

    @Override
    public Page<User> findAllByEmail(String email, Pageable pageable) {
        return userJpaRepository.findAllByEmailContainingIgnoreCase(email, pageable);
    }

    @Override
    public Page<User> findAllByUserRole(String userRole, Pageable pageable) {
        return userJpaRepository.findAllByUserRole(userRole, pageable);
    }

    @Override
    public Page<User> findAllByUserRoleAndEmail(String userRole, String email, Pageable pageable) {
        return userJpaRepository.findAllByEmailAndUserRole(email,userRole, pageable);
    }
}
