package com.example.demosecurity.repo;

import com.example.demosecurity.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppUserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String name);
    boolean existsByUsername(String username);
}
