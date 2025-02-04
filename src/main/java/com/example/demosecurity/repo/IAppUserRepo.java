package org.example.c08security.repo;

import org.example.c08security.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppUserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String name);
}
