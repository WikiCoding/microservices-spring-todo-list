package com.wikicoding.repository;

import com.wikicoding.entity.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserVO, Integer> {
    Optional<UserVO> findByEmail(String email);
}
