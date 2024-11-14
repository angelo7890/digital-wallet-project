package com.anjox.api_carteira.repository;

import com.anjox.api_carteira.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByemail(String email);
    UserEntity findByid(Long id);
    UserEntity findByusername(String username);
    boolean existsByusername(String username);
}
