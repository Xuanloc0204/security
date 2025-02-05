package com.example.demosecurity.repo;

import com.example.demosecurity.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppRoleRepo extends JpaRepository<AppRole, Long> {

    AppRole findByName(String name);
}
