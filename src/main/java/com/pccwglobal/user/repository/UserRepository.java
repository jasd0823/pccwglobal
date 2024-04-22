package com.pccwglobal.user.repository;
import com.pccwglobal.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByIdAndActiveTrue(Long id);

    List<User> findAllByActiveTrue();
}

