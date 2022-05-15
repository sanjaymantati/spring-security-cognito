package com.photoapp.auth.repository;

import com.photoapp.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);
}
