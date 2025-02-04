package org.example.c08security.repo;

import org.example.c08security.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppRoleRepo extends JpaRepository<AppRole, Long> {

    AppRole findByName(String name);
}
