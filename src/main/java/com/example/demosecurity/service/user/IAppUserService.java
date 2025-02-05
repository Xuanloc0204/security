package com.example.demosecurity.service.user;


import com.example.demosecurity.model.AppUser;
import com.example.demosecurity.service.GeneralService;

public interface IAppUserService extends GeneralService<AppUser> {
    boolean existsByUsername(String username);
}
